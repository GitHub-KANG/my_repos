package com.kjz.cloud.feign.service.controller;

import com.kjz.cloud.feign.service.feign.UserServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserFeignController {

    @Resource
    private UserServiceFeign userServiceFeign;

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {
        return userServiceFeign.getUser(id);
    }

}
