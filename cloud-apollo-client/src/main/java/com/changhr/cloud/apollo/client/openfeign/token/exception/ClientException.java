package com.changhr.cloud.apollo.client.openfeign.token.exception;

import com.changhr.cloud.apollo.client.common.ResultVO;
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
