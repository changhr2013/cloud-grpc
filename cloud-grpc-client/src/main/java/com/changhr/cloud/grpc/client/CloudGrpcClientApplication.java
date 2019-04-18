package com.changhr.cloud.grpc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author changhr
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CloudGrpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGrpcClientApplication.class, args);
    }

}
