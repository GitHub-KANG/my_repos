package com.kjz.cloud.redis.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kjz.cloud.redis.service.utils.RedisUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Resource
    private RedisUtil redisUtil;

    private static final String STRING_PREFIX = "STRING_";


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * set key value : 新增或更新字符串键值对
     * mset key value [key1 value1 ...]：批量新增或更新键值对
     * setnx key value ：如果key不存在就添加，否则就失败
     * setex key seconds value：设置简直对的时同时设置过期时间
     * get key ：获取指定key的值
     * mget key [key1 key2 ...]：获取多个key的值
     * del key [key1 key2 ...]：删除指定key
     * expire key seconds：设置指定key过期时间，以秒为单位
     * ttl key：查看指定key还剩余多长时间
     * incr key：将指定key存储的数值加1
     * decr key：将指定key存储的数值减1
     * incrby key step：将指定key存储的数值加上step
     * decrby key step ：将指定key存储的数值减去step
     */

    /**
     * 循环reids批量保存
     * @param param
     * @return
     */
    @RequestMapping(value = "/mset", method = RequestMethod.POST)
    public Object mset(@RequestBody String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        JSONObject obj = JSONObject.parseObject(param);
        if(Objects.isNull(obj)){
            return null;
        }
        Set<String> strings = obj.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            redisUtil.set(STRING_PREFIX + next, obj.get(next));
        }
        return "success";
    }

    /**
     * reids mset批量保存
     * @param param
     * @return
     */
    @RequestMapping(value = "/mset2", method = RequestMethod.POST)
    public Object mset2(@RequestBody String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        JSONObject obj = JSONObject.parseObject(param);
        if(Objects.isNull(obj)){
            return null;
        }

        Map<String, Object> map = Maps.newHashMap();

        Set<String> strings = obj.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            map.put(STRING_PREFIX + next, next);
        }
        redisUtil.mset(map);
        return "success";
    }

    @PostMapping ("/setnx")
    public Object setnx(@RequestParam("key") String key, @RequestParam("value") long value){
        key = STRING_PREFIX + key;
        boolean bool = redisUtil.setnx(key, value);
        if(!bool){
            return false;
        }
        log.info("setnx设置成功：{}", key);
        return true;
    }

    @PostMapping ("/expire")
    public Object expire(@RequestParam("key") String key, @RequestParam("expire") long expire){
        key = STRING_PREFIX + key;
        boolean bool = redisUtil.expire(key, expire);
        if(!bool){
            return false;
        }
        log.info("过期设置成功：{}", key);
        return true;
    }


    @PostMapping ("/incrstep")
    public Object incrstep(@RequestParam("key") String key, @RequestParam("value") long value){
        key = STRING_PREFIX + key;
        long result = redisUtil.incr(key, value);

        log.info("setnx设置result：{}", result);
        return result;
    }
}
