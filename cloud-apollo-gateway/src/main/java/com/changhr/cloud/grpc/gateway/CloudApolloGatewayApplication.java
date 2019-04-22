package com.changhr.cloud.grpc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author changhr
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CloudApolloGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApolloGatewayApplication.class, args);
    }

}
