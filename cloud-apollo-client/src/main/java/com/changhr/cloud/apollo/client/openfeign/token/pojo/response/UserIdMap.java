package com.changhr.cloud.apollo.client.openfeign.token.pojo.response;

import lombok.Data;

/**
 * 批量换取用户 ID 返回视图数据对象
 *
 * @author changhr
 * @create 2019-05-09 11:33
 */
@Data
public class UserIdMap {
    private String srcUserId;
    private String userId;
}
