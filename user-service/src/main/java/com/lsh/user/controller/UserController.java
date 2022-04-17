package com.lsh.user.controller;

import com.lsh.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utils.ResponseData;

import java.util.HashMap;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/13 9:53 下午
 * @desc ：
 */
@RestController
public class UserController {
    @Value("${server.port}")
    int port;
    @Value("${spring.application.name}")
    String name;



    @GetMapping("getUserById")
    public ResponseData getUserById(String id){
        System.out.println("getUserById 接收参数："+id);
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("name","zs");
        resp.put("age","20");
//        System.out.println(1/0);
        return ResponseData.success(resp);
    }

    @GetMapping("/getPort")
    public String getPort(){
        return "name:"+name+"  ;  prot:"+port;
    }


    @PostMapping("/postFromData")
    public ResponseData postFromdata(String name,Integer age){
        System.out.println("POST方法 表单格式 接收参数："+name+"-"+age);
        return ResponseData.success(name+"："+age);
    }
    @PostMapping("/postJson")
    public ResponseData postJson(@RequestBody UserDto dto){
        System.out.println("POST方法 表单格式 接收参数："+dto);
        return ResponseData.success(dto.getName()+"："+dto.getAge());
    }


}
