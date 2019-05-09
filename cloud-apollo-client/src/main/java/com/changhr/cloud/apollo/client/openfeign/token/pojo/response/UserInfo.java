package com.changhr.cloud.apollo.client.openfeign.token.pojo.response;

import lombok.Data;

/**
 * token 换取的用户信息数据对象
 *
 * @author changhr
 * @create 2019-05-09 11:26
 */
@Data
public class UserInfo {
    private Integer appId;
    private String srcUserId;
    private String userId;
}
