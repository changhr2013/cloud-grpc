package com.changhr.cloud.grpc.client.controller;

import com.changhr.cloud.grpc.client.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changhr
 */
@RestController
public class GrpcClientController {

    @Autowired
    private GrpcClientService grpcClientService;

    @RequestMapping("/")
    public String printMessage(@RequestParam(defaultValue = "Spring Cloud") String name) {
        return grpcClientService.sendMessage(name);
    }
}
