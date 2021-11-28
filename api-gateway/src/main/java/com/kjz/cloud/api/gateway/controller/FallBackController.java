package com.kjz.cloud.api.gateway.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FallBackController {

    @GetMapping("/fallback")
    public Object fallBack(){
        Map<String, Object> result = Maps.newHashMap();
        result.put("code",9999);
        result.put("message","服务异常");
        result.put("data", null);
        return result;
    }
}
