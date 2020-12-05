package com.darian.schoolmanager.login.configuration;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.login.DTO.UserDTO;
import com.darian.schoolmanager.login.utils.LoginUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2020/9/16  22:58
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserDTO userDTO = LoginUtils.getLoginUser();
        HttpSession session = request.getSession(false);
        // TODO : 跨域

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter printWriter = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("[sessionID]:" + session.getId());
        userDTO.setSessionId(session.getId());

        // 消除 userDTO 的密码
        userDTO.getUserDO().setPassword(null);

        if (userDTO == null) {
            LOGGER.info("注消成功...");
            String result = objectMapper.writeValueAsString(CustomerResponse.ok("注销成功", userDTO));
            printWriter.print(result);
        }
        session.setAttribute(userDTO.getUserDO().getUsername(), "ok");
        LOGGER.info(MessageFormat.format("用户{0}登录成功...", userDTO.getUsername()));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userDTO", userDTO);
        // 兼容 vue ,vue 的强制拦截。
        resultMap.put("access_token", session.getId());

        String result = objectMapper.writeValueAsString(CustomerResponse.ok("登陆成功", resultMap));
        printWriter.print(result);
    }
}
