package com.kjz.cloud.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public String  get(@PathVariable String id) {
        System.out.println("---id:---"+id);
       return "test get..."+id;
    }


}
