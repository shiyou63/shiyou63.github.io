package com.ajuro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.ajuro.common.repository.mapper")
public class AjuroBlog {

    public static void main(String[] args) {

        SpringApplication.run(AjuroBlog.class,args);
    }
}