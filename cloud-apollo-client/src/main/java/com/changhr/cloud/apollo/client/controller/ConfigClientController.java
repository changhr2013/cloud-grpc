package com.changhr.cloud.apollo.client.controller;

import com.changhr.cloud.apollo.client.common.ResultVO;
import com.changhr.cloud.apollo.client.feign.ticket.TicketApi;
import com.changhr.cloud.apollo.client.feign.ticket.TicketClient;
import com.changhr.cloud.apollo.client.feign.ticket.pojo.TicketMap;
import com.changhr.cloud.apollo.client.feign.token.TokenApi;
import com.changhr.cloud.apollo.client.feign.token.TokenApiFactory;
import com.changhr.cloud.apollo.client.feign.token.pojo.request.Ticket;
import com.changhr.cloud.apollo.client.feign.token.pojo.response.GetTokenRspData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @author changhr
 * @date 2019/4/21
 */
@RestController
@RequestMapping("configConsumer")
@RefreshScope
public class ConfigClientController {

    @Value("${config_info}")
    private String config;

    @RequestMapping("/getConfigInfo")
    public String getConfigInfo() {
        return config;
    }

    @RequestMapping("/ticket")
    public String getTicket() {
        TicketApi ticketApi = TicketClient.connect();
        return ticketApi.buildTicket(TicketMap.builder().appId(11).type(0).srcId("13500000061").build());
    }

    @PostMapping("/token")
    public ResultVO<GetTokenRspData> getToken(@RequestBody Ticket ticket) {
        TokenApi tokenApi = TokenApiFactory.INSTANCE.getInstance();
        return tokenApi.getToken(ticket);
    }
}
