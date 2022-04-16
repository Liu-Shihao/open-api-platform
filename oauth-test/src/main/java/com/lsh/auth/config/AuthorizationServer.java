package com.lsh.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/20 10:31 上午
 * @desc ：1. 认证服务首先配置：认证授权服务器
 * 继承 AuthorizationServerConfigurerAdapter
 * AuthorizationServerConfigurerAdapter 类中3个不同的configure方法分别
 * 1.1. 用来配置客户端详情服务
 * 1.2. 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)；
 * 1.3. 用来配置令牌端点(Token Endpoint)的安全约束；
 *
 * 授权服务配置总结:
 * 既然要完成认证，它首先需要知道客户端信息从哪读取，所以需要配置客户端详细信息
 * 既然要颁发token，就必须定义token的相关endpoint，以及token如何存取及客户端支持哪些类型的token
 * 既然暴露了这些endpoint，就需要对这些endpoint加上安全约束
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    /**
     * 客户端详情服务
     */
    @Autowired
    private ClientDetailsService clientDetailsService;


    /**
     * 认证管理器  在SpringSecurityConfig中配置
     */
    @Autowired
    private AuthenticationManager authorizationManager;

    /**
     * 授权码存储方式（JDBC）
     */
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    /**
     * 令牌管理 在TokenConfig配置
     */
    @Autowired
    private TokenStore tokenStore;


    /**
     * 1.配置客户端详细信息服务  使用JDBC模式，即将ClientDetails信息存储到数据库：
     * clients的pathMapping()方法用来配置端点的URL链接
     * /oauth/authorize 授权端点
     * /oauth/token     令牌端点
     * /oauth/confirm_access  用户确认授权提交端点
     * /oauth/error     授权服务错误信息端点
     * /oauth/check_token 用于资源服务访问的令牌解析端点
     * /oauth/token_key  提供公有秘钥的端点，比如使用JWT令牌  采用RSA非对称加密方式
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //将客户端信息存储到数据库
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 2.配置令牌的服务端点
     *  配置授权、配置访问端点、令牌服务、存储方式
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints

                .authenticationManager(authorizationManager)//认证管理器
                .authorizationCodeServices(authorizationCodeServices)//授权码存储服务（JDBC）
                .tokenServices(tokenServices())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);//允许的令牌端点请求方法
    }


    /**
     * 3.配置令牌端点的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                //  /oauth/token_key  公开的
                .tokenKeyAccess("permitAll()")
                // /oauth/check_token  是公开的
                .checkTokenAccess("permitAll()")
                //允许客户端的表单身份验证（申请令牌）
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置令牌管理服务
     * @return
     */
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

        defaultTokenServices.setClientDetailsService(clientDetailsService); //客户端详情服务

        defaultTokenServices.setSupportRefreshToken(true);//支持刷新令牌

        defaultTokenServices.setTokenStore(tokenStore);//令牌存储策略Redis

        defaultTokenServices.setAccessTokenValiditySeconds(7200);//令牌默认有效期 2小时 60*60*2

        defaultTokenServices.setRefreshTokenValiditySeconds(259200);//刷新令牌默认有效期3天

        return defaultTokenServices;
    }



}
