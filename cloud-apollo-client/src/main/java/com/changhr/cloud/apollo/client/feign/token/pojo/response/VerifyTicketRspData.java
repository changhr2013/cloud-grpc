package com.changhr.cloud.apollo.client.feign.token.pojo.response;

import lombok.Data;

/**
 * 验证 ticket 返回数据对象
 *
 * @author changhr
 * @create 2019-05-09 11:22
 */
@Data
public class VerifyTicketRspData {
    private Integer appId;
    private String srcUserId;
    private String userId;
    private Integer version;
}
