package com.kjz.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 在启动类上添加@EnableCircuitBreaker来开启Hystrix的断路器功能
 */

@EnableCircuitBreaker //TODO 高版本该注解已经过期
@EnableDiscoveryClient
@SpringBootApplication
public class HystrixServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixServiceApplication.class, args);
    }

}
