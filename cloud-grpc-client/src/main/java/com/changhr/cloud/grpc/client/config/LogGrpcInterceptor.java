package com.changhr.cloud.grpc.client.config;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类实现了 gRPC 的 ClientInterceptor，用来拦截请求，并输出请求的具体方法的日志，然后释放请求
 *
 * @author chang
 */
public class LogGrpcInterceptor implements ClientInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogGrpcInterceptor.class);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        log.info(methodDescriptor.getFullMethodName());
        return channel.newCall(methodDescriptor, callOptions);
    }
}
