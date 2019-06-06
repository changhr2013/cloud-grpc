package com.changhr.cloud.nacos.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudNacosClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudNacosClientApplication.class, args);
    }

}
