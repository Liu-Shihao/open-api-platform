package com.lsh.auth.dto;

import lombok.Data;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/15 5:18 下午
 * @desc ：
 */
@Data
public class OauthClientDetailsDto {

    private Long id;

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 资源ID集合，多个资源时用英文逗号分隔
     */
    private String resourceIds;
    /**
     * 客户端密匙
     */
    private String clientSecret;
    /**
     * 客户端申请的权限范围
     */
    private String scope;
    /**
     * 客户端支持的grant_type
     */
    private String authorizedGrantTypes;
    /**
     * 重定向URI
     */
    private String webServerRedirectUri;
    /**
     * 客户端所拥有的SpringSecurity的权限值，多个用英文逗号分隔
     */
    private String authorities;
    /**
     * 访问令牌有效时间值(单位秒)
     */
    private Integer accessTokenValidity;
    /**
     * 更新令牌有效时间值(单位秒)
     */
    private Integer refreshTokenValidity;
    /**
     * 预留字段
     */
    private String additionalInformation;
    /**
     * 用户是否自动Approval操作
     */
    private String autoapprove;

    private String createTime;

    private String updateTime;
    /**
     * 0 停用 1 在用
     */
    private int flag;
}
