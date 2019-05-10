package com.changhr.cloud.apollo.client.utils;

import com.changhr.cloud.apollo.client.common.ResultVO;
import com.changhr.cloud.apollo.client.enums.ResultEnum;

/**
 * 组装返回结果的工具类
 *
 * @author changhr
 * @create 2019-04-23 16:09
 */
public class ResultVOUtil {

    /**
     * 拼装带返回数据的成功结果
     * @param data 成功时返回的数据对象
     */
    public static ResultVO<Object> success(Object data) {
        return ResultVO.builder()
                .code(ResultEnum.SUCCESS.getCode())
                .desc(ResultEnum.SUCCESS.getDesc())
                .data(data).build();
    }

    /**
     * 拼装不带返回数据的成功结果
     */
    public static ResultVO<Object> success() {
        return success(null);
    }

    /**
     * 拼装请求失败的返回结果
     * @param resultEnum    失败结果枚举
     */
    public static ResultVO<Object> error(ResultEnum resultEnum) {
        return ResultVO.builder()
                .code(resultEnum.getCode())
                .desc(resultEnum.getDesc()).build();
    }

    /**
     * 拼装请求失败的返回结果
     * @param code  失败结果状态码
     * @param desc  失败结果描述信息
     */
    public static ResultVO<Object> error(Integer code, String desc) {
        return ResultVO.builder()
                .code(code)
                .desc(desc).build();
    }

    /**
     * 拼装请求失败的返回结果
     * @param resultEnum    失败结果枚举
     * @param desc          失败结果描述信息
     */
    public static ResultVO<Object> error(ResultEnum resultEnum, String desc) {
        return ResultVO.builder()
                .code(resultEnum.getCode())
                .desc(desc).build();
    }
}
