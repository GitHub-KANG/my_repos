package com.kjz.cloud.api.gateway.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * redis限流
 */
@Configuration
public class RedisRateLimiterConfig {

//    @Bean
//    @Qualifier("userKeyResolver")
//    public KeyResolver userKeyResolver(){
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userName"));
//    }

    @Bean
    @Qualifier("ipKeyResolver")
    KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
