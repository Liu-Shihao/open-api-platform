<h1>SpringSecurityOauth2.0</h1>

# 一、四种模式

**注意：**
**在WebSecurityConfig的在所有请求的安全验证配置中：**
 `http.formLogin() `是开启浏览器表单认证，如果这个没有开启，则**无法使用授权码模式认证和简单模式认证**，只能使用**密码认证**（需要使用UserDetailService的loadUserByUsername方法）和**客户端认证** 。

## 1.授权码模式（如要先页面登录认证） 

### 1.1 申请授权码 GET请求

申请授权码： http://localhost:8084/oauth/authorize?client_id=firstclient&client_secret=123456&response_type=code&scope=ALL&redirect_uri=https://www.baidu.com/



| Key           | Value                    |
| :------------ | ------------------------ |
| client_id     | 客户端ID                 |
| client_secret | 客户端秘钥               |
| grant_type    | authorization_cod        |
| redirect_uri  | 重定向地址(数据库中信息) |
| response_type | code                     |
| scope         | ALL(数据库中信息)        |



授权码携带在重定向的URL后：https://www.baidu.com/?code=M0Lxyk

### 1.2 申请令牌  POST请求 表单格式 

注意：重定向地址要和数据库中一致，并且授权码只能使用一次
申请令牌： http://localhost:8084/oauth/token

| Key           | Value             |
| :------------ | ----------------- |
| client_id     | 客户端ID          |
| client_secret | 客户端秘钥        |
| grant_type    | authorization_cod |
| code          | 授权码            |
| redirect_uri  | 重定向地址        |

```JSON
{
    "access_token": "6c4e14d2-ba58-4505-b512-1027358db1a0",
    "token_type": "bearer",
    "refresh_token": "7c23c445-8d5d-4914-b413-bc30872fa8c6",
    "expires_in": 1502,
    "scope": "ALL"
}
```

expires_in 令牌过期时间，单位为秒、



## 2.密码模式 POST请求

使用密码模式的话就不需要申请授权了，直接申请令牌，需要指定`grant_type`为`password`，携带`username`和`password`字段。

申请令牌： http://localhost:8084/oauth/token

| Key           | Value      |
| ------------- | ---------- |
| client_id     | 客户端ID   |
| client_secret | 客户端秘钥 |
| grant_type    | password   |
| redirect_uri  | 重定向地址 |
| username      | 用户名     |
| password      | 密码       |



```JSON
{
    "access_token": "6c4e14d2-ba58-4505-b512-1027358db1a0",
    "token_type": "bearer",
    "refresh_token": "7c23c445-8d5d-4914-b413-bc30872fa8c6",
    "expires_in": 801,
    "scope": "ALL"
}
```



## 3.简单模式 GET请求 直接返回令牌

简单模式就是在申请授权的时候，如果授权服务器认证成功，则直接返回令牌。

申请令牌：http://localhost:8084/oauth/authorize?client_id=firstclient&client_secret=123456&response_type=token&scope=ALL&redirect_uri=https://www.baidu.com/



| Key           | Value      |
| ------------- | ---------- |
| client_id     | 客户端ID   |
| client_secret | 客户端秘钥 |
| grant_type    | Token      |
| redirect_uri  | 重定向地址 |
| scope         | ALL        |



令牌拼接在重定向地址的参数中：

https://www.baidu.com/#access_token=6c4e14d2-ba58-4505-b512-1027358db1a0&token_type=bearer&expires_in=176

## 4. 客户端模式 POST请求 Form表单格式

客户端授权模式只需要客户端的ID和客户端秘钥进行申请令牌。

申请令牌： http://localhost:8084/oauth/token

| Key           | Value              |
| ------------- | ------------------ |
| client_id     | 客户端ID           |
| client_secret | 客户端秘钥         |
| grant_type    | client_credentials |

```json
{
    "access_token": "2ed0e693-6028-461c-beaf-0f4e54af1d3a",
    "token_type": "bearer",
    "expires_in": 5912,
    "scope": "ALL"
}
```

# 二、令牌校验  POST请求 

令牌校验端口：http://localhost:8084/oauth/check_token

Form-data 格式

| Key   | Value |
| ----- | ----- |
| token | 令牌  |

令牌校验成功返回：

返回信息为客户端的详情信息

```json
{
    "aud": [
        "all"
    ],
    "scope": [
        "ALL"
    ],
    "active": true,
    "exp": 1650183348,
    "client_id": "firstclient"
}
```

令牌校验失败：
状态码为400
```
{
    "error": "invalid_token",
    "error_description": "Token was not recognised"
}
```
# 三、请求流程

1. 请求网关申请令牌

   ​	网关转发转发请求到认证服务，颁发令牌

2. 携带令牌请求API

   ​	网关转发请求到认证服务，验证令牌，是否具有该API服务权限，	

   ​	验证通过后允许访问API服务




