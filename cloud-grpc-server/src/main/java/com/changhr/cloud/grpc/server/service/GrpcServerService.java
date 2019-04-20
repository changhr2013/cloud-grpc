package com.changhr.cloud.grpc.server.service;

import com.changhr.cloud.grpc.HelloReply;
import com.changhr.cloud.grpc.HelloRequest;
import com.changhr.cloud.grpc.SimpleGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * gRPC 的服务类 GrpcServerService，该类继承了 SimpleGrpc.SimpleImplBase
 *
 * @author changhr
 */
@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {

    @Override
    public void sayHello(HelloRequest request, io.grpc.stub.StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==========> " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
