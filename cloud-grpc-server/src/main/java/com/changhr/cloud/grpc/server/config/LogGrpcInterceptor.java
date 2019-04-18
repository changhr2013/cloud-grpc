package com.changhr.cloud.grpc.server.config;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类实现了 gRPC 的 ClientInterceptor，用来拦截请求，并输出请求的具体方法的日志，然后释放请求
 *
 * @author chang
 */
public class LogGrpcInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogGrpcInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info(serverCall.getMethodDescriptor().getFullMethodName());
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
