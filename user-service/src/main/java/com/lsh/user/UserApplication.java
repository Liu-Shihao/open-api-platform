package com.lsh.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/13 9:52 下午
 * @desc ：
 */
@EnableEurekaClient
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
        System.out.println("===User Service started on port 8083===");

    }
}
