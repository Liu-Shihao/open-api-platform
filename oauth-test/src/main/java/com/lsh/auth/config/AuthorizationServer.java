package com.lsh.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/20 10:31 上午
 * @desc ：授权服务器配置
 * 1. 配置客户端详细信息
 * 2. 配置令牌的服务端点和令牌服务（tokenServer）
 * 3. 配置令牌端点的安全约束
 * 授权服务配置总结:
 * 既然要完成认证，它首先需要知道客户端信息从哪读取，所以需要配置客户端详细信息
 * 既然要颁发token，就必须定义token的相关endpoint，以及token如何存取及客户端支持哪些类型的token
 * 既然暴露了这些endpoint，就需要对这些endpoint加上安全约束
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    /**令牌管理 在TokenConfig配置*/
    @Autowired
    private TokenStore tokenStore;

    /**客户端详情服务*/
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**授权码认证服务*/
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    /**认证管理器  在SpringSecurityConfig中配置*/
    @Autowired
    private AuthenticationManager authorizationManager;
    /**使用JWT令牌格式*/
    @Autowired
    JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    UserDetailServiceImpl userDetailService;




    /**
     * 配置令牌管理服务
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        //setClientDetailsService       客户端详情服务
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        //setSupportRefreshToken        支持刷新令牌
        defaultTokenServices.setSupportRefreshToken(true);
        //setTokenStore                 令牌存储策略
        defaultTokenServices.setTokenStore(tokenStore);

        //设置令牌增强 采用JWT令牌的方式
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        //setAccessTokenValiditySeconds 令牌默认有效期 2小时 60*60*2
        defaultTokenServices.setAccessTokenValiditySeconds(7200);
        //setRefreshTokenValiditySeconds 刷新令牌默认有效期3天
        defaultTokenServices.setRefreshTokenValiditySeconds(259200);

        return defaultTokenServices;

    }

    /**
     * 令牌端点的安全约束
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
     * 配置客户端详细信息
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

        //以下方式暂使用内存方式配置客户端详细信息
//        clients.inMemory()
//                //用来标识客户端的ID
//                .withClient("c1")
//                //客户端秘钥
//                .secret(new BCryptPasswordEncoder().encode("order"))
//                //资源列表
//                .resourceIds("order")
//                //该Client允许的授权类型
//                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")
//                //允许的授权范围
//                .scopes("all","order")
//                // 自动批准 false 跳转到授权页面
//                .autoApprove(false)
//                //加上验证回调地址
//                .redirectUris("http://www.baidu.com")
//                //使用and()可以配置多个客户端
//                .and()
//                .withClient("c2")
//                .secret(new BCryptPasswordEncoder().encode("account"))
//                .resourceIds("account")
//                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")
//                .scopes("all","account")
//                .autoApprove(false)
//                .redirectUris("http://www.baidu.com");
    }

    //配置令牌的服务端点
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //认证管理器
                .authenticationManager(authorizationManager)
                //授权码服务
                .authorizationCodeServices(authorizationCodeServices)
//                .userDetailsService(userDetailService)
                //令牌管理服务
                .tokenServices(tokenServices())
                //允许的令牌服务端点请求方法
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);

    }

    //授权码存储方式 :设置授权码模式的授权码如何存储
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        //采用JDBC数据库存储授权码
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    //JDBC客户端详情 服务
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource){
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(bCryptPasswordEncoder);
        return jdbcClientDetailsService;
    }
}
