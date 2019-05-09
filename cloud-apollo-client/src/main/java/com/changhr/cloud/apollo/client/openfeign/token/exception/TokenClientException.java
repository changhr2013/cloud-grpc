package com.changhr.cloud.apollo.client.openfeign.token.exception;

/**
 * Token client exception
 *
 * @author changhr
 * @create 2019-05-09 13:58
 */
public class TokenClientException extends RuntimeException {

    private String message;

    public TokenClientException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
