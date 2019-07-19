package com.changhr.cloud.grpc.cipher.virtual.pack.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段属性注解
 * @author changhr
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.TYPE})
public @interface ColumnProperty {

    // 字段类型
    ColumnType type();

    // 指定结构体对应的对象类
    Class clazz() default Class.class;

    // 如果指定长度，就不再获取长度标识位
    int length() default 0;
}
