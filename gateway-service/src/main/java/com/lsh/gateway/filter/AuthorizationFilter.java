package com.lsh.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/25 1:40 下午
 * @desc ：全局认证过滤器
 * 需要注入Spring容器
 */
@Slf4j
public class AuthorizationFilter implements GlobalFilter, Ordered {


    public String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Autowired
    RestTemplate restTemplate;

    @Value("${Authorization.checkUrl}")
    private  String checkUrl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("---====Gateway 认证开始====---");
        ServerHttpRequest request = exchange.getRequest();
        // /USER/getUserById
        String uri = request.getPath().toString();
        log.info("Gateway ：URI :"+uri+" ; TIME :"+ now);
        if (uri.contains("/oauth/")){
            // 请求认证服务   放行
            log.info("请求Auth认证服务-->");
            return chain.filter(exchange);
        }
        //非认证请求，校验token
        HttpHeaders headers = request.getHeaders();
        //获取请求头中的认证信息
        List<String> authorization = headers.get("Authorization");
        if (authorization == null || authorization.isEmpty() || authorization.size()==0 || authorization.get(0).length()<8){
            //没有认证信息 不允许访问，禁止访问
            log.info("Gateway: 没有令牌，拒绝访问！");
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("对不起，您没有权限访问！".getBytes());
            //返回401 未授权
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        }
        // Bearer token
        String access_token = authorization.get(0);
        //注意：令牌是从access_token值第7位开始取值（不包括第7位）
        String token = access_token.substring(7);
        try{
            System.out.println("请求认证服务校验令牌");
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("token",token);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(checkUrl, multiValueMap, String.class);
            int statusCodeValue = responseEntity.getStatusCodeValue();
            if (200 == statusCodeValue){
                //请求认证服务成功
                String entityBody = responseEntity.getBody();
                System.out.println("认证服务校验令牌返回："+entityBody);
                JSONObject jsonObject = JSON.parseObject(entityBody);
                JSONArray aud = JSON.parseArray(jsonObject.getString("aud"));
                JSONArray scope = JSON.parseArray(jsonObject.getString("scope"));
                // /USER/getUserById
                String application = uri.split("/")[1];
                System.out.println("要访问的服务："+application);
                if (!aud.contains(application)){
                    throw new RuntimeException("令牌无权限访问该服务");
                }
            }


        }catch (Exception e){
            log.error("令牌解析失败："+e.getMessage());
            DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("对不起，令牌校验失败，您没有权限访问！".getBytes());
            //返回401 未授权
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        }

        //添加请求头 信息
//        ServerHttpRequest newRequest = request.mutate().header("username", claims).build();

        ServerHttpRequest newRequest = request.mutate().build();

        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();


        Mono<Void> filter = chain.filter(newExchange);
        log.info("---====Gateway 认证结束====---");
        return filter;
    }

    /**
     * 过滤器有一个优先级的问题，这个值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
