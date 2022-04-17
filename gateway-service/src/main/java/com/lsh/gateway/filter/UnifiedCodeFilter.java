package com.lsh.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/17 3:55 下午
 * @desc ：统一解决中文乱码问题
 */
public class UnifiedCodeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}