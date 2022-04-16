package com.lsh.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/18 1:35 下午
 * @desc ：SpringSecurity认证
 *         继承UserDetailsService接口 实现loadUserByUsername方法
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailServiceImpl...username："+username);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("USER"));
        /**
         * 返回值是UserDetails，这是一个接口，
         * 一般使用它的子类org.springframework.security.core.userdetails.User，
         * 它有三个参数，分别是用户名、密码和权限集
         */
        return new User("LiuShihao", bCryptPasswordEncoder.encode("123456"),authorities);

    }
}
