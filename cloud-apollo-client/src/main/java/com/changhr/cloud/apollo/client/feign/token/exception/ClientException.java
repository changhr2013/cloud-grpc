package com.changhr.cloud.apollo.client.feign.token.exception;

import lombok.Getter;

/**
 * client exception
 *
 * @author changhr
 * @create 2019-05-09 13:58
 */
@Getter
public class ClientException extends RuntimeException {

    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }
}
