package com.changhr.cloud.apollo.client.openfeign.token;

import com.changhr.cloud.apollo.client.openfeign.token.exception.TokenErrorDecoder;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.request.*;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.response.*;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.*;
import feign.*;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;

import java.util.List;

/**
 * Token
 *
 * @author changhr
 * @create 2019-05-09 11:05
 */
@Headers("Content-Type: application/json")
public interface TokenApi {

    @RequestLine("POST /token_service/ticket/token")
    ResultVO<GetTokenRspData> getToken(Ticket ticket);

    @RequestLine("POST /token_service/ticket/verify")
    ResultVO<VerifyTicketRspData> verifyTicket(Ticket ticket);

    @RequestLine("POST /token_service/ticket/user_info")
    ResultVO<UserInfo> getUserInfo(Token token);

    @RequestLine("POST /token_service/ticket/user_ids")
    ResultVO<List<UserIdMap>> swapUserIdList(SwapUserIdListReq userIdListReq);

    @RequestLine("POST /token_service/ticket/src_user_ids")
    ResultVO<List<UserIdMap>> swapSrcUserIdList(SwapSrcUserIdListReq srcUserIdListReq);
}
