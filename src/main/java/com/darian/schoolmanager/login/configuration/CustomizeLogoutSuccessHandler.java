package com.darian.schoolmanager.login.configuration;

import com.darian.schoolmanager.common.utils.JSONUtils;
import com.darian.schoolmanager.common.modle.CustomerResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 退出登陆处理器
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/18  1:08
 */
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        // session 失效
        httpServletRequest.getSession().invalidate();

        CustomerResponse result = CustomerResponse.ok("退出登陆成功", null);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSONUtils.beanToJson(result));
    }
}
