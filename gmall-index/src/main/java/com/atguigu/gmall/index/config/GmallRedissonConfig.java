package com.atguigu.gmall.index.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/17 16:44
 * 分布式锁值Redisson
 * @Description: 解决redis集群状态下的问题
 */
@Configuration
public class GmallRedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://114.55.29.125:6379");
        return Redisson.create(config);
    }
}