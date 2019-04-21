package com.changhr.cloud.grpc.zuul.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * zuul 路由规则刷新类 ZuulPropertiesRefresher
 *
 * @author changhr
 * @date 2019/4/21
 */
@Component
public class ZuulPropertiesRefresher implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ZuulPropertiesRefresher.class);

    private ApplicationContext applicationContext;

    @Autowired
    private RouteLocator routeLocator;

    /**
     * 注解中的 value 参数默认是 application，因为自定义了 namespace 所以需要指定，
     * 监听服务端的配置下发，有配置更新时会调用 refreshZuulProperties() 方法进行参数刷新。
     *
     * @param changeEvent 配置改变事件
     */
    @ApolloConfigChangeListener(value = "TEST1.zuul-config")
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean zuulPropertiesChanged = false;
        for (String changedKey : changeEvent.changedKeys()) {
            if (changedKey.startsWith("zuul.")) {
                zuulPropertiesChanged = true;
                break;
            }
        }
        if (zuulPropertiesChanged) {
            refreshZuulProperties(changeEvent);
        }
    }

    /**
     * 参数刷新的方法
     *
     * @param changeEvent   配置改变的事件
     */
    private void refreshZuulProperties(ConfigChangeEvent changeEvent) {
        logger.info("Refreshing zuul properties!");

        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));

        this.applicationContext.publishEvent(new RoutesRefreshedEvent(routeLocator));

        logger.info("Zuul properties refreshed!");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
