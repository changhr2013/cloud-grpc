package com.changhr.cloud.apollo.client.openfeign.token.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author changhr
 * @create 2019-05-09 17:28
 */
@Configuration
public class TokenAPiConfig {

    @Value("${service.token.url}")
    private String tokenUrl;

    @Bean
    public TokenConnection tokenConnection(){
        return new TokenConnection(tokenUrl);
    }
}
