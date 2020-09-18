package com.beyond.cloud.alibaba.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BcaSvcStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BcaSvcStorageApplication.class, args);
    }

}
