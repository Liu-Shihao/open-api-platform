package com.lsh.auth.config;

import com.lsh.auth.handler.GatewayAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/18 11:49 上午
 * @desc ：
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    GatewayAccessDeniedHandler gatewayAccessDeniedHandler;

    /**
     * 认证管理器
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 指定加密方式为BCrypt
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全拦截机制  最重要
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
//                .antMatchers("/r/r1").hasAnyAuthority("p1")
//                .antMatchers("/login*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                //自定义登录成功的页面地址
//                .successForwardUrl("/login-success")
//                //自定义登出
//                .and()
//                .logout()
//                //登出URL
//                .logoutUrl("/logout")
//                //登出成功后的跳转页面   一般是登录页面
//                .logoutSuccessUrl("/login")
//                .and()
                .exceptionHandling()
                //设置无权限的处理类
                .accessDeniedHandler(gatewayAccessDeniedHandler);
    }
    @Override
    public void configure(WebSecurity web) {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

}
