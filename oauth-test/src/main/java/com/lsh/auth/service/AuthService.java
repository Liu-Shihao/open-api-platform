package com.lsh.auth.service;

import com.lsh.auth.dto.OauthClientDetailsDto;
import org.springframework.stereotype.Service;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/15 5:25 下午
 * @desc ：
 */

public interface AuthService {
    void createClientDetail(OauthClientDetailsDto clientDetailsDto);
}
