package com.guli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Guli07UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(Guli07UcenterApplication.class);
    }
}
