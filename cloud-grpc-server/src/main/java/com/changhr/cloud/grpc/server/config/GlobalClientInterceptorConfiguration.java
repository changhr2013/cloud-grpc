package com.changhr.cloud.grpc.server.config;

import io.grpc.ClientInterceptor;
import net.devh.springboot.autoconfigure.grpc.client.GlobalClientInterceptorRegistry;
import net.devh.springboot.autoconfigure.grpc.server.GlobalServerInterceptorConfigurerAdapter;
import net.devh.springboot.autoconfigure.grpc.server.GlobalServerInterceptorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 注入一个 GlobalClientInterceptorConfigurerAdapter 的 Bean，在 Bean 中注册一个 LogGrpcInterceptor
 *
 * @author changhr
 */
@Order
@Configuration
public class GlobalClientInterceptorConfiguration {

    @Bean
    public GlobalServerInterceptorConfigurerAdapter globalClientInterceptorConfigurerAdapter() {
        return new GlobalServerInterceptorConfigurerAdapter () {
            @Override
            public void addServerInterceptors(GlobalServerInterceptorRegistry registry) {
                registry.addServerInterceptors(new LogGrpcInterceptor());
            }
        };
    }
}
