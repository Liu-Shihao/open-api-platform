<h1>SpringCLoud Gateway网关</h1>

# 路由转发
## 自动转发路由
通过注册中心（Eureka/Nacos）注册的服务名自动转发

## routes
通过在application.yml配置routes配置，匹配断言过滤，路由转发

# 负载均衡
## 自动负载均衡
## 通过lb://服务名
## 自定义负载均衡策略


# Filter过滤器
过滤器配置可以直接在application.yml文件的routes下配置

## RequestRateLimiter
RequestRateLimiter是网关的请求限流过滤器

## HystrixGatewayFilterFactory
HystrixGatewayFilterFactory是网关的服务熔断降级过滤器，当服务不能提供服务时，就会触发网关的熔断降级方法

## GlobalFilter
GlobalFilter是全局过滤器，我们可以通过配置全局过滤器实现对请求的鉴权、日志记录等操作