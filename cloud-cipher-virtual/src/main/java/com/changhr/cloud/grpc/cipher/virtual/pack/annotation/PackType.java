package com.changhr.cloud.grpc.cipher.virtual.pack.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 包类型
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface PackType {

    /**
     * 包类型号
     * @return short
     */
    short typeNo();
}
