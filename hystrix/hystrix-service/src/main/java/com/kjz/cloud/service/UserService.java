package com.kjz.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @HystrixCommand中的常用参数
 * fallbackMethod：指定服务降级处理方法；
 * ignoreExceptions：忽略某些异常，不发生服务降级；
 * commandKey：命令名称，用于区分不同的命令；
 * groupKey：分组名称，Hystrix会根据不同的分组来统计命令的告警及仪表盘信息；
 * threadPoolKey：线程池名称，用于划分线程池。
 */
@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @HystrixCommand(fallbackMethod = "getDefaultUser")
    public String getUser(@PathVariable Long id) {
        String result = restTemplate.getForObject(userServiceUrl + "/user/{1}", String.class, id);
        System.out.println("==result："+result);
        return result;
    }

    public String getDefaultUser(@PathVariable Long id) {
        System.out.println("==getDefaultUser=="+id);
        return "defaultUser"+id;
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser",
    groupKey = "commandGroupKey",
            threadPoolKey = "commandThreadPoolKey",
            commandKey = "commandKey1"
    )
    public String testCommand(Long id) {
        String result = restTemplate.getForObject(userServiceUrl + "/user/{1}", String.class, id);
        System.out.println("==testCommand result："+result);
        return result;
    }

    /**根据异常选择是否降级**/
    @HystrixCommand(fallbackMethod = "getDefaultUser2", ignoreExceptions = {NullPointerException.class})
    public String getUserException(Long id) {
        if (id == 1) {
            throw new IndexOutOfBoundsException();
        } else if (id == 2) {
            throw new NullPointerException();
        }
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", String.class, id);
    }

    public String getDefaultUser2(@PathVariable Long id, Throwable e) {
        System.err.println("getDefaultUser2 id:"+id+",throwable class:"+ e.getClass());
        return "jiangji_"+id;
    }



    /**Hystrix的请求缓存
     * @CacheResult：开启缓存，默认所有参数作为缓存的key，cacheKeyMethod可以通过返回String类型的方法指定key；
     * @CacheKey：指定缓存的key，可以指定参数或指定参数中的属性值为缓存key，
     * cacheKeyMethod还可以通过返回String类型的方法指定；
     * @CacheRemove：移除缓存，需要指定commandKey
     *
     * 注意：在每个请求前后初始化和关闭HystrixRequestContext来解决该问题
     * **/
    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getUserCache")
    public String getUserCache(Long id) {
        System.out.println("getUserCache id:"+ id);
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", String.class, id);
    }

    /**
     * 为缓存生成key的方法
     */
    public String getCacheKey(Long id) {
        return String.valueOf(id);
    }

    @CacheRemove(commandKey = "getUserCache", cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public void removeCache(Long id) {
        System.out.println("removeCache start id:"+ id);
        String result = restTemplate.getForObject(userServiceUrl + "/user/{1}", String.class, id);
        System.out.println("removeCache end key:"+id +",result:"+ result);
    }

    /**
     * 请求合并
     * 微服务系统中的服务间通信，需要通过远程调用来实现，随着调用次数越来越多，占用线程资源也会越来越多。
     * Hystrix中提供了@HystrixCollapser用于合并请求，从而达到减少通信消耗及线程数量的效果。
     *
     * @HystrixCollapser的常用属性
     * batchMethod：用于设置请求合并的方法；
     * collapserProperties：请求合并属性，用于控制实例属性，有很多；
     * timerDelayInMilliseconds：collapserProperties中的属性，用于控制每隔多少时间合并一次请求；
     */
    //使用@HystrixCollapser实现请求合并，所有对getUserFuture的的多次调用都会转化为对getUserByIds的单次调用


    @HystrixCollapser(batchMethod = "getUserByIds",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")
    })
    public Future<Long> getUserFuture(final Long id) {
        return new AsyncResult<Long>(){
            @Override
            public Long invoke() {
                String commonResult = restTemplate.getForObject(userServiceUrl + "/user/{1}", String.class,id);
                System.out.println("getUserById username:{}"+ id);
                return id;
            }
        };
    }

    @HystrixCommand
    public List<Long> getUserByIds(List<Long> ids) {
        System.out.println("getUserByIds execute:"+ids);
        return ids;
    }
}
