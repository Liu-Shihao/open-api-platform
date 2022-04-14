package com.lsh.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author ：LiuShihao
 * @date ：Created in 2021/8/16 10:21 上午
 * @desc ：
 */
@Component
public class MyRateLimiterResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        InetAddress address = remoteAddress.getAddress();
        String hostAddress = address.getHostAddress();
        System.out.println("MyRateLimiterResolver : "+hostAddress);

        RequestPath path = exchange.getRequest().getPath();

        return Mono.just(path.toString());
    }
}
