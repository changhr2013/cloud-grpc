package com.changhr.cloud.grpc.client.config;

import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


/**
 * @author changhr
 */
@Configuration
@Order
public class GlobalClientInterceptorConfiguration {

    @Bean
    public GlobalClientInterceptorConfigurer globalClientInterceptorConfigurer() {
        return clientInterceptorRegistry -> clientInterceptorRegistry.addClientInterceptors(new LogGrpcInterceptor());
    }
}
