package com.changhr.cloud.apollo.client.feign.token;

import com.changhr.cloud.apollo.client.feign.ticket.TicketApi;
import com.changhr.cloud.apollo.client.feign.ticket.TicketClient;
import com.changhr.cloud.apollo.client.feign.ticket.pojo.TicketMap;
import com.changhr.cloud.apollo.client.common.ResultVO;
import com.changhr.cloud.apollo.client.feign.token.pojo.request.SwapSrcUserIdListReq;
import com.changhr.cloud.apollo.client.feign.token.pojo.request.SwapUserIdListReq;
import com.changhr.cloud.apollo.client.feign.token.pojo.request.Ticket;
import com.changhr.cloud.apollo.client.feign.token.pojo.request.Token;
import com.changhr.cloud.apollo.client.feign.token.pojo.response.GetTokenRspData;
import com.changhr.cloud.apollo.client.feign.token.pojo.response.UserIdMap;
import com.changhr.cloud.apollo.client.feign.token.pojo.response.UserInfo;
import com.changhr.cloud.apollo.client.feign.token.pojo.response.VerifyTicketRspData;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TokenClientTest {

    @Autowired
    private TokenClient tokenClient;

    private static TokenApi TokenApi;
    private static TicketApi ticketApi = TicketClient.connect();

    @Test
    public void test01GetToken() {
        TokenApi = TokenApiFactory.INSTANCE.getInstance();
        String ticket = ticketApi.buildTicket(TicketMap.builder().type(0).appId(11).srcId("13500000042").build());

        ResultVO<GetTokenRspData> tokenResult = TokenApi.getToken(new Ticket(ticket));
        Assert.assertEquals(0, tokenResult.getCode().intValue());

        GetTokenRspData data = tokenResult.getData();
        System.out.println(data.getUserToken());
        System.out.println(data.getUserTokenExpiresIn());
    }

    @Test
    public void test011GetToken() {
        String ticket = ticketApi.buildTicket(TicketMap.builder().type(0).appId(11).srcId("13500000042").build());
        GetTokenRspData token = tokenClient.getToken(ticket);
        System.out.println(token);
    }

    @Test
    public void test02VerifyTicket() {
        String ticket = ticketApi.buildTicket(TicketMap.builder().type(1).appId(11).srcId("13500000051").build());

        ResultVO<VerifyTicketRspData> verifyResult = TokenApi.verifyTicket(new Ticket(ticket));
        Assert.assertEquals(0, verifyResult.getCode().intValue());
        VerifyTicketRspData data = verifyResult.getData();
        System.out.println(data);
    }

    @Test
    public void test03GetUserInfo() {
        String token = "a9262bfb691f4268973d3d7cc6202b77";
        ResultVO<UserInfo> userInfoResult = TokenApi.getUserInfo(new Token(token));

        Assert.assertEquals(0, userInfoResult.getCode().intValue());
        UserInfo data = userInfoResult.getData();
        System.out.println(data);
    }

    @Test
    public void test04SwapUserIdList() {
        String token = "a9262bfb691f4268973d3d7cc6202b77";
        SwapUserIdListReq userIdListReq = SwapUserIdListReq.builder()
                .userToken(token)
                .srcUserIds(Arrays.asList("13500000051", "13500000041", "13500000042"))
                .build();

        ResultVO<List<UserIdMap>> listResultVO = TokenApi.swapUserIdList(userIdListReq);
        Assert.assertEquals(0, listResultVO.getCode().intValue());
        listResultVO.getData()
                .forEach(userIdMap -> System.out.println(userIdMap.getSrcUserId() + " -> " + userIdMap.getUserId()));
    }

    @Test
    public void test05SwapSrcUserIdList() {
        String token = "a9262bfb691f4268973d3d7cc6202b77";

        SwapSrcUserIdListReq srcUserIdListReq = SwapSrcUserIdListReq.builder()
                .userToken(token)
                .userIds(Arrays.asList("ee9e49d7d2544acda8cb118eb9732b27", "04bd383035d0472c9a1b1d78971d454a", "99170933d0904659a91cb4c5ca9fc536"))
                .build();

        ResultVO<List<UserIdMap>> listResultVO = TokenApi.swapSrcUserIdList(srcUserIdListReq);
        Assert.assertEquals(0, listResultVO.getCode().intValue());
        listResultVO.getData()
                .forEach(userIdMap -> System.out.println(userIdMap.getUserId() + " -> " + userIdMap.getSrcUserId()));
    }
}