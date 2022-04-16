package com.lsh.auth.controller;

import com.lsh.auth.dto.OauthClientDetailsDto;
import com.lsh.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utils.ResponseData;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/15 5:04 下午
 * @desc ：
 */
@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/createClientDetail")
    public ResponseData createClientDetail(@RequestBody @Validated OauthClientDetailsDto clientDetailsDto){
        try {
            authService.createClientDetail(clientDetailsDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseData.error();
        }
        return ResponseData.success();
    }


}
