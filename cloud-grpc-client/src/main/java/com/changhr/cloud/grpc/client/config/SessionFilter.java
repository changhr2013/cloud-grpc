package com.changhr.cloud.grpc.client.config;

import brave.propagation.ExtraFieldPropagation;
import org.springframework.cloud.sleuth.instrument.web.SleuthWebProperties;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 自定义过滤器
 * 获取当前的 SessionId，放入 Baggage 中
 * 注意，因为不是所有的请求都需要往后传递，所以会对一些请求跳过执行
 *
 * @author changhr
 */
@Component
@Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER + 1)
public class SessionFilter extends GenericFilterBean {

    private Pattern skipPattern = Pattern.compile(SleuthWebProperties.DEFAULT_SKIP_PATTERN);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("Filter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        boolean skip = skipPattern.matcher(httpRequest.getRequestURI()).matches();

        if (!skip) {
            // 将 SessionId 放到 Baggage 中
            ExtraFieldPropagation.set("SessionId", httpRequest.getSession().getId());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
