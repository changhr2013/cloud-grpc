package com.changhr.cloud.apollo.client.exception;

import com.changhr.cloud.apollo.client.common.ResultVO;
import com.changhr.cloud.apollo.client.enums.ResultEnum;
import com.changhr.cloud.apollo.client.openfeign.token.exception.ClientException;
import com.changhr.cloud.apollo.client.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局统一异常处理
 *
 * @author changhr
 * @create 2019-05-10 15:35
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO<Object> unknownErrorHandler(HttpServletRequest request, Exception e) {
        log.error("the method " + request.getMethod() + " occurred global unknown exception, please check the problem! ", e);
        return ResultVOUtil.error(ResultEnum.UNKNOWN_ERROR);
    }
}
