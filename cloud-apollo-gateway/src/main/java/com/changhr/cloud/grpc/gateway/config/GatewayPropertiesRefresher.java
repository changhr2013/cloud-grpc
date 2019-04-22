package com.changhr.cloud.grpc.gateway.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author changhr
 */
@Component
public class GatewayPropertiesRefresher implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(GatewayPropertiesRefresher.class);

    private ApplicationContext applicationContext;
    private final GatewayProperties gatewayProperties;
    private final DynamicRouteServiceImpl dynamicRouteService;

    public GatewayPropertiesRefresher(GatewayProperties gatewayProperties, DynamicRouteServiceImpl dynamicRouteService) {
        this.gatewayProperties = gatewayProperties;
        this.dynamicRouteService = dynamicRouteService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ApolloConfigChangeListener(value = "TEST1.gateway-config")
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean gatewayPropertiesChanged = false;

        for (String changedKey : changeEvent.changedKeys()) {
            // 前缀 spring.cloud.gateway 的 key 发生了改变（gateway 的配置发生了改变）
            if (changedKey.startsWith("spring.cloud.gateway")) {
                gatewayPropertiesChanged = true;
                break;
            }
        }

        // 更新 gateway 配置
        if (gatewayPropertiesChanged) {
            refreshGatewayProperties(changeEvent);
        }
    }

    /**
     * 更新 SpringApplicationContext 对象，并更新路由
     * @param changeEvent   变化事件
     */
    private void refreshGatewayProperties(ConfigChangeEvent changeEvent) {

        log.info("Refreshing Gateway properties!");

        // 更新配置
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));

        // 更新路由
        gatewayProperties.getRoutes().forEach(dynamicRouteService::update);

        log.info("Gateway properties refreshed!");
    }
}
