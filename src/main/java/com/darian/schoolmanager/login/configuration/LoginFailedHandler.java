package com.darian.schoolmanager.login.configuration;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.login.utils.LoginUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2020/9/16  22:55
 */
public class LoginFailedHandler implements AuthenticationFailureHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFailedHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        // // TODO : 跨域
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter printWriter = response.getWriter();
        CustomerResponse result;

        if (exception instanceof InternalAuthenticationServiceException) {
            result = CustomerResponse.error(exception.getMessage());

        } else if (exception instanceof SessionAuthenticationException) {
            LOGGER.debug("SessionAuthenticationException：");
            result = CustomerResponse.error("会话管理异常");
        } else if (exception instanceof BadCredentialsException) {
            //密码错误
            result = CustomerResponse.error("密码错误");
        } else if (exception instanceof UsernameNotFoundException) {
            result = CustomerResponse.error(exception.getMessage());
        } else {
            // AuthenticationException
            result = CustomerResponse.error(exception.getMessage());
        }
        printWriter.print(objectMapper.writeValueAsString(result));
        printWriter.close();
    }
}
