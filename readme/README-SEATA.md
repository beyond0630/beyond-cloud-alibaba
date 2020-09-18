# SpringCloud + OpenFeign 集成 Seata
## 1. 前置条件
* 基于支持本地 ACID 事务的关系型数据库。
* Java 应用，通过 JDBC 访问数据库。
* 每个微服务已完成对 MySQL + Druid + Mybatis 的集成

## 2. Docker 安装 seata server
* 请参考 [Docker 安装 Seata Server](https://github.com/beyond0630/beyond-cloud-alibaba-deploy/blob/master/infra/seata-deploy.sh)

## 3. 集成步骤
### 1) 导入包
```pom

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-seata</artifactId>
</dependency>

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-nacos-discovery</artifactId>
</dependency>
```

### 2) application.yml 添加对 seata 配置
```yaml
spring:
  cloud:
    alibaba:
      seata:
        tx-service-group: beyond_alibaba_tx_group          # 事物组

seata:
  enabled: true
  enable-auto-data-source-proxy: true                      # 开启数据库自动代理
  registry:
    type: nacos
    nacos:
      server-addr: ${LINEX_HOST:192.168.178.129}:8848
      namespace: public
      cluster: seata-server
      group: SEATA_GROUP
  config:
    type: nacos
    nacos:
      server-addr: ${LINEX_HOST:192.168.178.129}:8848
      namespace: public
```

* [Seata register.conf](./seata/register.conf)
* [Seata 所有配置](./seata/config.txt)

### 3) Seata AT 模式需要 undo_log 表
* 每个参与分布式事物的数据库添加 undo_log 表 
```sql
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
```

### 4) 数据源代理
* 这个是要特别注意的地方, seata 对数据源做了代理和接管, 在每个参与分布式事务的服务中, 都要做如下配置.
> 启动类关闭数据源自动配置
```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(BcaSvcAccountApplication.class, args);
    }
}

```

> 添加数据源配置
```java
/**
 * 数据源代理
 *
 * @author lucifer
 * @date 2020/7/29 16:57
 */
@Configuration
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
public class DataSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:/com/beyond/cloud/alibaba/**/mapper/**/*.xml"));
        SqlSessionFactory factory;
        try {
            factory = bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return factory;
    }
}
```
* 为适应 DruidDataSource 能正常实例, 数据源修改为如下配置
```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: JDBC_URL
      username: DB_USER
      password: DB_PASSWORD
      initial-size: 5
      max-active: 20
```

### 5) 开启分布式事物
* 在有通过 Feign 远程微服务调用且涉及事物的业务层方法加 @GlobalTransactional
```java
@Service
public class BusinessServiceImpl implements BusinessService {
    /**
     * 采购
     */
    @Override
    @GlobalTransactional(name = "purchase", rollbackFor = Exception.class)
    public ApiResult purchase(final int userId, final String commodityCode, final int orderCount) {
        ApiResult<Boolean> deductResult = storageClient.deduct(commodityCode, orderCount);
        ApiResult<Order> orderResult = orderClient.createOrder(userId, commodityCode, orderCount);
        if (orderCount > 1) {
            throw new BusinessException("每人限购一份");
        }
        return ApiResult.ok();
    }
}
```
