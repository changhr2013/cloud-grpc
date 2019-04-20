package com.changhr.cloud.grpc.client.service;

import com.changhr.cloud.grpc.HelloReply;
import com.changhr.cloud.grpc.HelloRequest;
import com.changhr.cloud.grpc.SimpleGrpc;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import static com.changhr.cloud.grpc.SimpleGrpc.newBlockingStub;

/**
 * @author changhr
 */
@Service
public class GrpcClientService {

    @GrpcClient("cloud-grpc-server")
    private Channel serverChannel;

    public String sendMessage(String name) {
        SimpleGrpc.SimpleBlockingStub stub = newBlockingStub(serverChannel);
        HelloReply response = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }
}
