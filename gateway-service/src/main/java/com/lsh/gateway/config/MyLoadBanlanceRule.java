package com.lsh.gateway.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/14 1:36 下午
 * @desc ：自定义负载均衡规则
 */
public class MyLoadBanlanceRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
//        System.out.println("MyLoadBanlanceRule...");
        List<Server> reachableServers = this.getLoadBalancer().getReachableServers();//获得所有可达的服务
//        System.out.println(reachableServers);
        //逻辑判断
        return reachableServers.get(0);//则固定返回第一个服务
    }
}
