package com.changhr.cloud.grpc.cipher.virtual.pack.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段属性注解
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface ColumnProperty {

    // 字段类型
    ColumnType type();

    // 字段长度限制，0 为不限制
    //int maxLen() default 0;
}
