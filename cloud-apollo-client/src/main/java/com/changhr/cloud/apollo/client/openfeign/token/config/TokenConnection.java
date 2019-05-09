package com.changhr.cloud.apollo.client.openfeign.token.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token 连接参数对象
 *
 * @author changhr
 * @create 2019-05-09 17:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenConnection {
    private String url;
}
