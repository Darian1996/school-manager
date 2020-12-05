package com.darian.schoolmanager.configuration.filter;

import org.slf4j.MDC;
import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2020/11/23  上午3:24
 */
@WebFilter
@Component
public class TraceIdFilter implements OrderedFilter {

    private static String traceId_key = "traceId";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String tranceValue = httpServletRequest.getHeader("traceId");
        if (StringUtils.isEmpty(tranceValue)) {
            tranceValue = UUID.randomUUID().toString().replaceAll("-", "");
        }
        MDC.put(traceId_key, tranceValue);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public int getOrder() {
        return OrderedFilter.HIGHEST_PRECEDENCE;
    }

}
