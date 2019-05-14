package com.changhr.cloud.apollo.client.feign.token.pojo.response;

import lombok.Data;

/**
 * 获取 Token 返回数据对象
 *
 * @author changhr
 * @create 2019-05-09 11:10
 */
@Data
public class GetTokenRspData {
    private String userToken;
    private Long userTokenExpiresIn;
}
