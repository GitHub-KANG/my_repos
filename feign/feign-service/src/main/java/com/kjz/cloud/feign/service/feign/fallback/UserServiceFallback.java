package com.kjz.cloud.feign.service.feign.fallback;

import com.kjz.cloud.feign.service.feign.UserServiceFeign;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserServiceFeign {
    @Override
    public String getUser(Long id) {
        return "服务getUser降级："+id;
    }
}
