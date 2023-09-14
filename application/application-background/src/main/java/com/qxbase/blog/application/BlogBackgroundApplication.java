package com.qxbase.blog.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.qxbase.blog.**"})
//@MapperScan({"com.qxbase.blog.**.mapper"})
public class BlogBackgroundApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogBackgroundApplication.class, args);
    }
}