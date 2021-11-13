package com.kjz.cloud.feign.service.feign;

import com.kjz.cloud.feign.service.feign.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", fallback = UserServiceFallback.class)
public interface UserServiceFeign {

    /**
     * 注意
     * 使用Feign的时候,如果参数中带有
     * @PathVariable形式的参数,
     * 则要用value=""标明对应的参数,否则会抛出IllegalStateException异常，
     * 或启动报
     * Feign PathVariable annotation was empty on param 0
     */
    @GetMapping("/user/{id}")
    String getUser(@PathVariable("id") Long id);
}
