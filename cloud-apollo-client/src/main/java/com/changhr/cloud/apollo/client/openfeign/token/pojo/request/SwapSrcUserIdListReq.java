package com.changhr.cloud.apollo.client.openfeign.token.pojo.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DDOE 用户 ID 交换原始用户 ID
 *
 * @author changhr
 * @create 2019-05-09 11:45
 */
@Data
@Builder
public class SwapSrcUserIdListReq {
    private String userToken;
    private List<String> userIds;
}
