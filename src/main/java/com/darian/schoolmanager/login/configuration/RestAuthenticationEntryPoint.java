package com.darian.schoolmanager.login.configuration;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/***
 * 自定义实现 没有权限的 提示日志： JSON 结果返回，没有权限的话，走这个
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/17  23:09
 */
@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authenticationException) throws IOException, ServletException {

        LOGGER.debug("RestAuthenticationEntryPoint：", authenticationException);

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        String requestURI = httpServletRequest.getRequestURI();
        Map<String, Object> dataBody = new HashMap<>();
        dataBody.put("requestURI", requestURI);
        dataBody.put("ExceptionName", authenticationException.getClass().getSimpleName());
        dataBody.put("ExceptionMessage", authenticationException.getMessage());

        CustomerResponse result;
        if (authenticationException instanceof AnonymousAuthenticationException) {
            result = CustomerResponse.error("NEED_TO_LOGIN", "尚未登录，请先登录!", dataBody);
        } else if (authenticationException instanceof InternalAuthenticationServiceException) {
            result = CustomerResponse.error(authenticationException.getMessage());
        } else {
            result = CustomerResponse.error(authenticationException.getMessage());
        }

        PrintWriter out = httpServletResponse.getWriter();
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
