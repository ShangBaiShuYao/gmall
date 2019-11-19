package com.atguigu.gmall.wms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@MapperScan(basePackages = "com.atguigu.gmall.wms.dao")
@EnableFeignClients
public class GmallWmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallWmsApplication.class, args);
    }

}
