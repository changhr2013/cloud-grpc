package com.changhr.cloud.apollo.client.openfeign.basic;

import lombok.Data;

/**
 * 实体类
 *
 * @author changhr
 * @create 2019-05-09 10:05
 */
@Data
public class Contributor {
    private String login;
    private int contributions;
}
