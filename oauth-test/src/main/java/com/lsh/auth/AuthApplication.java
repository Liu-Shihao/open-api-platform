package com.lsh.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/14 6:08 下午
 * @desc ：
 */
@EnableEurekaClient
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
        System.out.println("---====认证授权服务启动 PORT：8084====---");
    }
}
