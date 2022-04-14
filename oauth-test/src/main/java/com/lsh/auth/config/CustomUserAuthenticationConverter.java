package com.lsh.auth.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/24 9:03 上午
 * @desc ：用户身份验证转换器，简单理解就是可以给Token中加入额外的扩展数据
 */
@Component
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        LinkedHashMap response = new LinkedHashMap();
        String name = authentication.getName();
        response.put("user_name", name);
        response.put("user_age",18);
        //根据自己的情况增加 扩展数据
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            //权限
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }
}
