package com.onestop.wx.cp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.onestop.wx"})
public class CpApplication {
    public static void main(String[] args) {
        SpringApplication.run(CpApplication.class, args);
    }
}
