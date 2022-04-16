package com.lsh.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/20 12:46 下午
 * @desc ：使用Redis存储令牌
 */
@Configuration
public class TokenConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 使用Redis存储令牌
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        //使用Redis存储令牌
        return new RedisTokenStore(redisConnectionFactory);
    }

}
