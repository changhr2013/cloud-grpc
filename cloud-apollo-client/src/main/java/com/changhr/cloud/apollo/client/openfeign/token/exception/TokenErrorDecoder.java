package com.changhr.cloud.apollo.client.openfeign.token.exception;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

import java.io.IOException;

/**
 * Token error decoder
 *
 * @author changhr
 * @create 2019-05-09 14:01
 */
public class TokenErrorDecoder implements ErrorDecoder {

    private final Decoder decoder;
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    public TokenErrorDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            return (Exception)decoder.decode(response, TokenClientException.class);
        } catch (IOException fallbackToDefault) {
            return defaultDecoder.decode(methodKey, response);
        }
    }
}
