package com.lsh.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/9/15 9:04 上午
 * @desc ：
 */
@Configuration
public class BeanConfig {

    /**
     * 指定加密方式
     */
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 授权码存储方式 :设置授权码模式的授权码如何存储
     * @param dataSource
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        //采用JDBC数据库存储授权码
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 客户端详情服务（JDBC模式）
     * @param dataSource
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource){
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(bCryptPasswordEncoder);//指定加密方式
        return jdbcClientDetailsService;
    }
}
