package com.changhr.cloud.apollo.client.openfeign.token;

import com.changhr.cloud.apollo.client.openfeign.token.exception.ClientException;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.ResultVO;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.request.Ticket;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.response.GetTokenRspData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * token 客户端
 *
 * @author changhr
 * @create 2019-05-09 11:58
 */
@Slf4j
@Component
public class TokenClient {

    @Autowired
    private Gson gson;

    public GetTokenRspData getToken(String ticket) {
        TokenApi tokenApi = TokenApiFactory.INSTANCE.getInstance();
        ResultVO<GetTokenRspData> resultVO = tokenApi.getToken(new Ticket(ticket));
        if (!resultVO.getCode().equals(0)) {
            throw new ClientException(gson.toJson(resultVO));
        }
        return resultVO.getData();
    }
}

