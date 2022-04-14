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
 * @desc ：Bean配置类
 */
@Configuration
public class TokenConfig {

    @Autowired
    CustomUserAuthenticationConverter customUserAuthenticationConverter;

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

    /**
     * 令牌存储方案  使用JWT格式
     * @return
     */
//    @Bean
//    public TokenStore tokenStore(){
//        //使用JWT存储令牌
//        return new JwtTokenStore(accessTokenConverter());
//    }



    /**使用对称加密*/
//    public  String SigningKey = "oauth123";
    /**
     * 使用JWT存储令牌   对称加密
     * @return
     */
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter(){
//        //使用JWT存储令牌
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        //对称秘钥 资源服务器使用该秘钥进行验证
//        jwtAccessTokenConverter.setSigningKey(SigningKey);
//        return jwtAccessTokenConverter;
//    }


    /**创建jks文件时的别名-alias ims.abc.com*/
//    public String rsaAlias = "rsafirst";
//    /**创建jks文件时的访问密码-keypass */
//    public String rsaPassword = "rsapassword";
//    /**RSA证书  */
//    public String certificateFileName = "rsa_first.jks";
    /**
     * 使用JWT存储令牌   RSA非对称加密
     * @return
     */
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter(){
//        //使用JWT存储令牌
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        //创建 密钥存储密钥工厂
//        ClassPathResource classPathResource = new ClassPathResource(certificateFileName);
//
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,rsaPassword.toCharArray());
//
//        //设置密钥对（私钥） 此处传入的是创建jks文件时的别名-alias 和 秘钥库访问密码
//        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(rsaAlias));
//
//        //用户身份验证转换器 给Token中加入额外的扩展数据
//        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) jwtAccessTokenConverter.getAccessTokenConverter();
//        accessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);
//        //在资源服务 验证JWT时 使用公钥进行解密 如：Order服务
//        return jwtAccessTokenConverter;
//    }


}
