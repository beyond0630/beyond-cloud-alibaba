package com.beyond.cloud.alibaba.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class BcaSvcBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BcaSvcBusinessApplication.class, args);
    }

}
