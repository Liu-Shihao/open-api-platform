package com.lsh.auth.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 客户端信息(OauthClientDetails)实体类
 *
 * @author LiuShihao
 * @since 2022-04-15 17:07:44
 */
@Data
@Entity
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = 325951732056729918L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private Date createTime;

    private Date updateTime;
    /**
     * 0 停用 1 在用
     */
    private int flag;
}

