package com.guli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Guli09VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(Guli09VodApplication.class);
    }
}
