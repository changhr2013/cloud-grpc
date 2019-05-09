package com.changhr.cloud.apollo.client.openfeign.token.pojo.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 原始用户ID 交换 DDOE 用户 ID
 *
 * @author changhr
 * @create 2019-05-09 11:30
 */
@Data
@Builder
public class SwapUserIdListReq {
    private String userToken;
    private List<String> srcUserIds;
}
