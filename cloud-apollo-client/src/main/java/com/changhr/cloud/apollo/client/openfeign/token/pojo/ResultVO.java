package com.changhr.cloud.apollo.client.openfeign.token.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * 返回的公共对象
 *
 * @author changhr
 * @create 2019-04-23 16:06
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ResultVO<T> {

    private Integer code;

    private String desc;

    private T data;
}
