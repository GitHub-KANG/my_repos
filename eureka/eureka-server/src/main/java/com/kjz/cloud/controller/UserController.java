//package com.kjz.cloud.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Random;
//
//@RefreshScope
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Value("${test.value}")
//    private String testValue;
//
//    @Value("${test.value2}")
//    private String testValue2;
//
//    @GetMapping("/{id}")
//    public String  get(@PathVariable String id) {
//        System.out.println("---id:---"+testValue);
//        System.out.println("---id2:---"+testValue2);
//        int i = new Random().nextInt();
//        return "test get..."+testValue2+"-"+i;
//    }
//
//
//}
