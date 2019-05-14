package com.changhr.cloud.apollo.client.feign.token;

/**
 * 获取 TokenApi 的工厂，使用枚举实现单例模式
 *
 * @author changhr
 */
public enum TokenApiFactory {
    /**
     * 单例
     */
    INSTANCE;

    private TokenApi instance;

    TokenApiFactory() {
        instance = TokenBoot.connect();
    }

    public TokenApi getInstance() {
        return instance;
    }
}
