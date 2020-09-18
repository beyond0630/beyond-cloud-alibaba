## **`项目简介`**
`spring cloud alibaba` 是一个学习 Spring Cloud Alibaba 全家桶微服务的演示项目, 并集成一些分布式中间件

Spring cloud alibaba 组件

| 组件名称                                                          | 组件介绍                           |
| --------------------------------------------------------------  | --------------------------------- |
| [SpringCloud Alibaba Nacos](https://github.com/alibaba/nacos/)  | 服务注册和配置中心                    |

## 各 Module 介绍

| Module 名称                                                 | Module 介绍                           |
| ---------------------------------------------------------- | ------------------------------------ |
| [beyond-cloud-common](./beyond-cloud-common)               | 公共模块                               |
| [beyond-cloud-framework](./beyond-cloud-framework)         | 中间件配置模块                          |
| [beyond-cloud-svc-account](./beyond-cloud-svc-account)     | 账户模块                               |
| [beyond-cloud-svc-business](./beyond-cloud-svc-business)   | 业务模块                               |
| [beyond-cloud-svc-order](./beyond-cloud-svc-order)         | 订单模块                               |
| [beyond-cloud-svc-storage](./beyond-cloud-svc-storage)     | 库存模块                               |

## 中间件
### 1. 分布式事物管理 -- Seata
* Seata 是一款开源的分布式事务解决方案, 致力于在微服务架构下提供高性能和简单易用的分布式事务服务.
* 注册中心: Nacos 配置中心: Nacos
* 关键配置

| 配置项                                       | 参数值                      | 备注                |
| --------------------------------------------| ---------------------------|--------------------|
| spring.cloud.alibaba.seata.tx-service-group | beyond_alibaba_tx_group    | 事物组名称           |
| seata.registry.type                         | nacos                      | 注册中心类型         |
| seata.registry.nacos.cluster                | seata-server               | 注册的服务名          |
| seata.config.type                           | nacos                      | 配置中心类型          |

* [详细集成配置](./readme/README-SEATA.md)
