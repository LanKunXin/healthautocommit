package com.example.healthautocommit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.example.healthautocommit.mapper")
public class HealthautocommitApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthautocommitApplication.class, args);
    }

}
