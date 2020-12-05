package com.darian.schoolmanager.login.configuration;

import com.darian.schoolmanager.common.utils.JSONUtils;
import com.darian.schoolmanager.common.modle.CustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/18  1:20
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {

    private Logger LOGGER = LoggerFactory.getLogger(CustomerAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String requestURI = httpServletRequest.getRequestURI();
        Map<String, Object> dataBody = new HashMap<>();
        dataBody.put("requestURI", requestURI);
        dataBody.put("ExceptionName", e.getClass().getSimpleName());
        dataBody.put("ExceptionMessage", e.getMessage());

        CustomerResponse result = CustomerResponse.error("NOT_HAVE_ACCESS", e.getMessage(), dataBody);

        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.print(JSONUtils.beanToJson(result));
        printWriter.close();
    }
}
