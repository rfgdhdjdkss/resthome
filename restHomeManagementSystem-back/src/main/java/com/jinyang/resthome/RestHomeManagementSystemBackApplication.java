package com.jinyang.resthome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.jinyang.resthome")
public class RestHomeManagementSystemBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestHomeManagementSystemBackApplication.class, args);
    }

}
