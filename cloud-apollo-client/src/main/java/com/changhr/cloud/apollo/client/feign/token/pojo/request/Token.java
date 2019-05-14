package com.changhr.cloud.apollo.client.feign.token.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * token 对象
 *
 * @author changhr
 * @create 2019-05-09 14:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String userToken;
}
