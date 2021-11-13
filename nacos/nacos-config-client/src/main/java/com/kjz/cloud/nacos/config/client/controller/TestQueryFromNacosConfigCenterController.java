package com.kjz.cloud.nacos.config.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 从配置中心获取配置信息值
 */
@RefreshScope
@RestController
@RequestMapping("/query")
public class TestQueryFromNacosConfigCenterController {

    @Value("${config.info:}")
    private String configInfo;

    @RequestMapping(value = "/getConfigInfo", method = RequestMethod.GET)
    public String getConfigInfo(){
        return "result:"+configInfo;
    }

}
