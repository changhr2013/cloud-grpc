package com.changhr.cloud.apollo.client.openfeign.token;

import com.changhr.cloud.apollo.client.openfeign.token.config.TokenConnection;
import com.changhr.cloud.apollo.client.openfeign.token.exception.TokenClientException;
import com.changhr.cloud.apollo.client.openfeign.token.exception.TokenErrorDecoder;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.ResultVO;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.request.Ticket;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.response.GetTokenRspData;
import com.google.gson.Gson;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * token 客户端
 *
 * @author changhr
 * @create 2019-05-09 11:58
 */
@Slf4j
@Component
public class TokenClient {

    private static String tokenUrl;

    @Autowired
    private TokenConnection tokenConnection;

    @PostConstruct
    public void init() {
        tokenUrl = tokenConnection.getUrl();
    }

    private static Gson gson = new Gson();

    public static GetTokenRspData getToken(String ticket) {
        TokenApi tokenApi = TokenApiFactory.INSTANCE.getInstance();
        ResultVO<GetTokenRspData> resultVO = tokenApi.getToken(new Ticket(ticket));
        if (!resultVO.getCode().equals(0)) {
            throw new TokenClientException(gson.toJson(resultVO));
        }
        return resultVO.getData();
    }

    static TokenApi connect() {
        Decoder decoder = new GsonDecoder();
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .errorDecoder(new TokenErrorDecoder(decoder))
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.FULL)
                .target(TokenApi.class, tokenUrl);
    }

}

