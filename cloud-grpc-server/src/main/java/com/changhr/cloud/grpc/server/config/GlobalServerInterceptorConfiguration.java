package com.changhr.cloud.grpc.server.config;

import net.devh.boot.grpc.server.interceptor.GlobalServerInterceptorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 注入一个 GlobalClientInterceptorConfigurerAdapter 的 Bean，在 Bean 中注册一个 LogGrpcInterceptor
 *
 * @author changhr
 */
@Configuration
@Order
public class GlobalServerInterceptorConfiguration {

    @Bean
    public GlobalServerInterceptorConfigurer globalServerInterceptorConfigurer() {
        return serverInterceptorRegistry -> serverInterceptorRegistry.addServerInterceptors(new LogGrpcInterceptor());
    }
}
