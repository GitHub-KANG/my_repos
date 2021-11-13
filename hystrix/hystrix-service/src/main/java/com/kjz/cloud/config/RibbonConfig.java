package com.kjz.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
 * 可以看出使用Ribbon的负载均衡功能非常简单，和直接使用RestTemplate没什么两样，
 * 只需给RestTemplate添加一个@LoadBalanced即可。
 */
@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
