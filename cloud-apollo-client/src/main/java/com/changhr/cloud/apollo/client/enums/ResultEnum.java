package com.changhr.cloud.apollo.client.enums;

/**
 * 返回结果枚举类
 *
 * @author changhr
 * @create 2019-04-23 16:11
 */
public enum ResultEnum {

      /** 用户请求 API 返回的状态结果集 */
      SUCCESS(0, "success")
    , UNKNOWN_ERROR(100001, "unknown error.")
    , PARAMETER_ERROR(100002, "Parametric verification failed.")
    ;

    private Integer code;
    private String desc;

    ResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
