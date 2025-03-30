package com.ajuro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 35238
 * @date 2023/7/18 0018 19:13
 */

@SpringBootApplication
@MapperScan("com.ajuro.common.repository.mapper")
public class AjuroBlog {

    public static void main(String[] args) {
        SpringApplication.run(AjuroBlog.class,args);
    }
}