package com.changhr.cloud.apollo.client.openfeign.token.exception;

/**
 * client exception
 *
 * @author changhr
 * @create 2019-05-09 13:58
 */
public class ClientException extends RuntimeException {

    private String message;

    public ClientException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
