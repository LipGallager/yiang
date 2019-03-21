package com.yiang.quartz02;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@MapperScan("com.yiang.quartz02.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class Quartz02Application {

    public static void main(String[] args) {
        SpringApplication.run(Quartz02Application.class, args);
    }

}
