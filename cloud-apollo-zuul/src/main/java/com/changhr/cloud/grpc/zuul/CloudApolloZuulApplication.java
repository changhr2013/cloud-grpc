package com.changhr.cloud.grpc.zuul;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author changhr
 */
@EnableApolloConfig
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class CloudApolloZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApolloZuulApplication.class, args);
    }

}
