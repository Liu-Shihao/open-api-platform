package com.lsh.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class SpringGatewayTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGatewayTestApplication.class, args);
        System.out.println("===Gateway Service started on port 8081===");
    }
}
