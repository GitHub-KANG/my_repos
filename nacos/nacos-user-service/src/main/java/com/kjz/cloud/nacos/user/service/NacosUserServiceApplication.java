package com.kjz.cloud.nacos.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosUserServiceApplication.class, args);
    }

}
