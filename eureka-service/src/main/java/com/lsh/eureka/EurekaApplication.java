package com.lsh.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/13 9:19 下午
 * @desc ：
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
        System.out.println("===Eureka Service started on port 8082===");

    }
}
