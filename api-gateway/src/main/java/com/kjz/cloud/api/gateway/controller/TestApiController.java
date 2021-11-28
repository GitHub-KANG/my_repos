package com.kjz.cloud.api.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestApiController {

    @GetMapping("/index")
    public String index(){
        return "success";
    }

//    @GetMapping("/user/{id}")
//    public String index(@PathVariable("id") Integer id){
//        return "success_"+id;
//    }
}
