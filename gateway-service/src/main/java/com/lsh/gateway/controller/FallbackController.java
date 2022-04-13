package com.lsh.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/13 10:57 下午
 * @desc ：
 */
@RestController
public class FallbackController {

    @GetMapping(value = "/gatewayfallback",produces = "text/html;charset=UTF-8")
    public String hystrix(){
        System.out.println("执行Gateway降级方法");
        String body = "<html><body><br><br><br><div style='width:800px; margin:auto; text-align:center; font-size:24px' >服务器繁忙，请稍后再试！ </div></body></html>";
        return body;
    }
}
