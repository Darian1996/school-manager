package com.darian.schoolmanager.login.utils;

import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.login.DO.UserDO;
import com.darian.schoolmanager.login.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2020/9/16  22:50
 */
public class LoginUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUtils.class);

    public static UserDTO getLoginUser() {
        checkCallerClassName();

        SecurityContext context = SecurityContextHolder.getContext();

        LOGGER.debug("[LoginUtils#getLoginUser],ThreadName" + Thread.currentThread().getName());
        if (context == null) {
            return null;
        }
        if (context.getAuthentication() == null || context.getAuthentication().getPrincipal() == null) {
            return null;
        }
        if (context.getAuthentication().getPrincipal() instanceof UserDetails) {
            Object principal = context.getAuthentication().getPrincipal();
            UserDTO userDTO = (UserDTO) principal;
            LOGGER.debug("[用户]:" + userDTO);
            return userDTO;
        }
        return null;
    }

    public static Long getUserId() {
        checkCallerClassName();
        UserDTO userDTO = getLoginUser();
        AssertUtils.assertTrue(Objects.nonNull(userDTO), "用户没有登录");
        UserDO userDO = userDTO.getUserDO();
        AssertUtils.assertTrue(Objects.nonNull(userDO), "登录用户信息查不到");
        return userDO.getId();
    }

    /**
     * 检查调用的类是否合法，只允许在 Controller 调用，以便于后期的拆分
     */
    private static void checkCallerClassName() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement targetStackTraceElement = elements[3];
        String callerClassName = targetStackTraceElement.getClassName();
        if (callerClassName.contains("Controller")
                || callerClassName.contains("LoginSuccessHandler")) {
            return;
        }
        AssertUtils.throwError("LoginUtils只能在[*controller]|[LoginSuccessHandler调用]");
    }
}
