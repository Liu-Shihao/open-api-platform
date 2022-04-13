package com.lsh.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/13 9:53 下午
 * @desc ：
 */
@RestController
public class UserController {

    @GetMapping("getUserById")
    public Map<String,Object> getUserById(String id){
        System.out.println("getUserById 接收参数："+id);
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("name","zs");
        resp.put("age","20");
//        System.out.println(1/0);
        return resp;
    }
}
