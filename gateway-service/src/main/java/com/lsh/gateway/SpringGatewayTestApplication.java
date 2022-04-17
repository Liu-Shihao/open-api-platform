package com.lsh.gateway;

import com.lsh.gateway.filter.AuthorizationFilter;
import com.lsh.gateway.filter.UnifiedCodeFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@EnableEurekaClient
@SpringBootApplication
public class SpringGatewayTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGatewayTestApplication.class, args);
        System.out.println("===Gateway Service started on port 8081===");
    }

    @Bean
    public AuthorizationFilter authFilter(){ return new AuthorizationFilter(); }
    @Bean
    public UnifiedCodeFilter codeFilter(){ return new UnifiedCodeFilter(); }


    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        //解决中文乱码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;

    }
}
