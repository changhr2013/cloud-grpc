package com.changhr.cloud.apollo.client.openfeign.ticket.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * build ticket 参数
 *
 * @author changhr
 * @create 2019-05-09 14:29
 */
@Data
@Builder
public class TicketMap {
    private Integer type;
    private Integer appId;
    private String srcId;
}
