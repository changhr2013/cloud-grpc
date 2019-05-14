package com.changhr.cloud.apollo.client.feign.ticket;

import com.changhr.cloud.apollo.client.feign.ticket.pojo.TicketMap;
import feign.QueryMap;
import feign.RequestLine;

/**
 * Build Ticket 远程接口
 *
 * @author changhr
 * @create 2019-05-09 14:24
 */
public interface TicketApi {

    @RequestLine("GET /ticket")
    String buildTicket(@QueryMap TicketMap ticketMap);
}
