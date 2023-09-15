package com.qxbase.blog.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.qxbase.blog.**"})
@MapperScan({"com.qxbase.blog.**.mapper"})
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class BlogReceptionApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogReceptionApplication.class, args);
    }
}