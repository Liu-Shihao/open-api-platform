package com.lsh.gateway;

import com.lsh.gateway.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class SpringGatewayTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGatewayTestApplication.class, args);
        System.out.println("===Gateway Service started on port 8081===");
    }

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }
}
