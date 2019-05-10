package com.changhr.cloud.apollo.client.openfeign.token;

import com.changhr.cloud.apollo.client.openfeign.token.pojo.request.*;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.response.*;
import com.changhr.cloud.apollo.client.openfeign.token.pojo.*;
import feign.*;
import java.util.List;

/**
 * Token
 *
 * @author changhr
 * @create 2019-05-09 11:05
 */
@Headers("Content-Type: application/json")
public interface TokenApi {

    /**
     * 获取 Token
     * @param ticket ticket 数据包
     * @return Token 数据包
     */
    @RequestLine("POST /token_service/ticket/token")
    ResultVO<GetTokenRspData> getToken(Ticket ticket);

    /**
     * 验证并解析 Token
     * @param ticket ticket 数据包
     * @return ticket 解包数据
     */
    @RequestLine("POST /token_service/ticket/verify")
    ResultVO<VerifyTicketRspData> verifyTicket(Ticket ticket);

    /**
     * Token 换取用户信息
     * @param token 用户 Token
     * @return 当前用户信息
     */
    @RequestLine("POST /token_service/ticket/user_info")
    ResultVO<UserInfo> getUserInfo(Token token);

    /**
     * 换取用户中心 ID
     * @param srcUserIdListReq 源用户 ID 请求包
     * @return 源用户 ID - 用户 ID 映射 Map
     */
    @RequestLine("POST /token_service/ticket/user_ids")
    ResultVO<List<UserIdMap>> swapUserIdList(SwapUserIdListReq srcUserIdListReq);

    /**
     * 换取源用户 ID
     * @param userIdListReq 用户中心 ID 请求包
     * @return 源用户 ID - 用户 ID 映射 Map
     */
    @RequestLine("POST /token_service/ticket/src_user_ids")
    ResultVO<List<UserIdMap>> swapSrcUserIdList(SwapSrcUserIdListReq userIdListReq);
}
