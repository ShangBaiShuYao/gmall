package com.atguigu.gmall.index.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/*
 * @Description
 * @Date   2019/11/17 16:42
 */
@Configuration
public class GmallJedisConfig {

    @Bean
    public JedisPool jedisPool(){

        /*
         * @Description 还可以设置一些最大连接数最小连接数等等,只有你想不到,没有他做不到
         * @Date   2019/11/17 16:58
         */
        return new JedisPool("114.55.29.125", 6379);
    }
}