package com.atguigu.gmall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/*
 *  @作者  上白书妖
 *  @Date  2019/11/8 19:41
 *  @Email shangbaishuyao@163.com
 *  @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GmallSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallSearchApplication.class, args);
    }

}
