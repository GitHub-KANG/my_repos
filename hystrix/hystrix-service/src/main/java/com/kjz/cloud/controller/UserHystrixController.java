package com.kjz.cloud.controller;

import com.kjz.cloud.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 创建UserHystrixController接口用于调用user-service服务
 */

@RestController
@RequestMapping("/user")
public class UserHystrixController {

    @Resource
    private UserService userService;

    @GetMapping("/testFallback/{id}")
    public String testFallback(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/testCommand/{id}")
    public String testCommand(@PathVariable Long id) {
        return userService.testCommand(id);
    }

    @GetMapping("/testException/{id}")
    public String testException(@PathVariable Long id) {
        return userService.getUserException(id);
    }

    /**hystrix 缓存**/
    @GetMapping("/testCache/{id}")
    public String testCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        return"ok";
    }

    /**hystrix 移除缓存**/
    @GetMapping("/testRemoveCache/{id}")
    public String testRemoveCache(@PathVariable Long id) {
        userService.getUserCache(id);
        userService.removeCache(id);
        userService.getUserCache(id);
        return"ok";
    }


    /**合并请求**/
    @GetMapping("/testCollapser")
    public String testCollapser() throws ExecutionException, InterruptedException {
        Future<Long> future1 = userService.getUserFuture(1L);
        Future<Long> future2 = userService.getUserFuture(2L);
        future1.get();
        future2.get();
        Thread.sleep(200);
        Future<Long> future3 = userService.getUserFuture(3L);
        future3.get();
        return "ok";
    }

}
