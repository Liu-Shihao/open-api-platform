package com.lsh.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/16 3:46 下午
 * @desc ：
 */
@RestController
@RequestMapping("/login")
public class LoginController {


    @RequestMapping(value = "/success",produces = "text/plain;charset=UTF-8")
    public String loginSuccess(){
        return " 登录成功~";
    }

}
