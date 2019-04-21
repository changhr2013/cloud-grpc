package com.changhr.cloud.apollo.client.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @author changhr
 * @date 2019/4/21
 */
@Configuration
@EnableApolloConfig(value = "application", order = 10)
public class AppConfig {

}
