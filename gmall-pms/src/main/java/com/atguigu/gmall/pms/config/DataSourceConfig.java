package com.atguigu.gmall.pms.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//@Configuration
public class DataSourceConfig {

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DruidDataSource druidDataSource() {
//        return new DruidDataSource();
//    }
//
//    /**
//     * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
//     *
//     * @param druidDataSource The DruidDataSource
//     * @return The default datasource
//     */
//    @Primary
//    @Bean("dataSource")
//    public DataSource dataSource(DruidDataSource druidDataSource) {
//        return new DataSourceProxy(druidDataSource);
//    }
}