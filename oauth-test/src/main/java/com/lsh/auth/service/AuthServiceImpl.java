package com.lsh.auth.service;

import com.lsh.auth.dto.OauthClientDetailsDto;
import com.lsh.auth.entity.OauthClientDetails;
import com.lsh.auth.repository.OauthClientDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/15 5:25 下午
 * @desc ：
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    OauthClientDetailsRepository clientDetailsRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createClientDetail(OauthClientDetailsDto clientDetailsDto) {
        OauthClientDetails details = new OauthClientDetails();
        BeanUtils.copyProperties(clientDetailsDto, details);
        details.setCreateTime(new Date());
        details.setFlag(1);
        details.setClientSecret(bCryptPasswordEncoder.encode(details.getClientSecret()));
        clientDetailsRepository.save(details);
    }
}
