package com.changhr.cloud.security.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源提供端的配置
 * @author changhr
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 这里设置需要 token 验证的 url
     * 可以在 WebSecurityConfigurerAdapter 中排除掉，
     * 对于相同的 url，如果二者都配置了验证
     * 则优先进入 ResourceServerConfigurerAdapter,进行 token 验证。而不会进行
     * WebSecurityConfigurerAdapter 的 basic auth 或表单认证。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/hi")
                .and()
                .authorizeRequests()
                .antMatchers("/hi")
                .authenticated();
    }

}
