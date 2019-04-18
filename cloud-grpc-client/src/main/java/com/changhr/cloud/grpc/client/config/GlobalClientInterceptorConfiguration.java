package com.changhr.cloud.grpc.client.config;

import net.devh.springboot.autoconfigure.grpc.client.GlobalClientInterceptorConfigurerAdapter;
import net.devh.springboot.autoconfigure.grpc.client.GlobalClientInterceptorRegistry;
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
    public GlobalClientInterceptorConfigurerAdapter globalClientInterceptorConfigurerAdapter() {
        return new GlobalClientInterceptorConfigurerAdapter () {
            @Override
            public void addClientInterceptors(GlobalClientInterceptorRegistry registry) {
                registry.addClientInterceptors(new LogGrpcInterceptor());
            }
        };
    }
}
