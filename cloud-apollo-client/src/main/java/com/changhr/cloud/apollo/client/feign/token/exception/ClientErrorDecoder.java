package com.changhr.cloud.apollo.client.feign.token.exception;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

import java.io.IOException;

/**
 * error decoder
 *
 * @author changhr
 * @create 2019-05-09 14:01
 */
public class ClientErrorDecoder implements ErrorDecoder {

    private final Decoder decoder;
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    public ClientErrorDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            return (Exception)decoder.decode(response, ClientException.class);
        } catch (IOException fallbackToDefault) {
            return defaultDecoder.decode(methodKey, response);
        }
    }
}
