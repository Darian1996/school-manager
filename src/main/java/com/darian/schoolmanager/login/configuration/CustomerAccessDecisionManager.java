package com.darian.schoolmanager.login.configuration;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomerAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {

        if (authentication instanceof AnonymousAuthenticationToken) {
            // 用户未登录
            throw new AnonymousAuthenticationException("用户没有登录, 该请求非法!!!");
        }

        /**
         * authentication： 当前登录用户信息
         * o：当前请求对象(FilterInvocation对象)
         * collection：FilterInvocationSecurityMetadataSource接口实现类中getAttributes方法的返回值
         * */
        for (ConfigAttribute attribute : collection) {

            //获取当前登录用户的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(attribute.getAttribute())) {
                    return;      //当前登录用户具备所需的角色 则无需判断
                }
            }
        }
        throw new AccessDeniedException("用户没有对应的权限, 该请求非法!!!, 需要的权限：" + collection);
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}