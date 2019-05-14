package com.changhr.cloud.apollo.client.feign.token;

import com.changhr.cloud.apollo.client.feign.token.exception.ClientErrorDecoder;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 用来配置和实例化 TokenApi Feign 客户端的起步类
 *
 * @author changhr
 * @create 2019-05-10 11:44
 */
@Slf4j
@Component
public class TokenBoot {

    private static String url;

    @Value("${service.token.url}")
    private String tokenUrl = "http://token.senseyun.com";

    @PostConstruct
    public void init() {
        url = tokenUrl;
    }

    static TokenApi connect() {
        Decoder decoder = new GsonDecoder();
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .errorDecoder(new ClientErrorDecoder(decoder))
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.FULL)
                .target(TokenApi.class, url);
    }

}