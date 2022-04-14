package com.lsh.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/14 2:50 下午
 * @desc ：认证鉴权过滤
 * 注意：还需要将此类注入Spring容器 @Bean
 */
public class AuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //前置过滤
        URI uri = exchange.getRequest().getURI();
        System.out.println("AuthFilter : "+uri+" ; date : "+new Date().toString());
        String s = uri.toString();

//        拦截请求设置返回响应
//        if (s.contains("USER")){
//            exchange.getResponse().setStatusCode(HttpStatus.BAD_GATEWAY);
//            return exchange.getResponse().setComplete();
//        }

        Mono<Void> mono = chain.filter(exchange);
//        System.out.println("AuthFilter：后置过滤");
        //后置过滤

        return mono;
    }

    @Override
    public int getOrder() {
        //数值越小，则过滤器越靠前
        return 0;
    }
}
