package com.kjz.cloud.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public String  get(@PathVariable String id, HttpServletRequest request) {
        System.out.println("---id:---"+id+" request:"+request.getParameter("name"));
       return "test get..."+id;
    }


}
