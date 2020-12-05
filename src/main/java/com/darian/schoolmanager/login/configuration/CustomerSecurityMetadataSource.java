package com.darian.schoolmanager.login.configuration;

import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DO.SecurityDO;
import com.darian.schoolmanager.login.service.PermissionSecurityService;
import com.darian.schoolmanager.login.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerSecurityMetadataSource.class);

    @Resource
    private SecurityService securityService;

    @Resource
    private PermissionSecurityService permissionSecurityService;

    //路径匹配符 直接用以匹配路径
    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        /*根据请求地址 分析请求该地址需要什么角色*/
        String requestUrl = ((FilterInvocation) o).getRequestUrl();    //获取请求地址
        LOGGER.debug("请求的地址:{}", requestUrl);

        List<SecurityDO> securityDOList = securityService.selectEnableAllAndOrdered();

        LOGGER.debug("请求所有的接口列表：{}", securityDOList);

        List<PermissionDO> permissionDOList = new ArrayList<>();

        boolean hasMatchDataBaseUrlPattern = false;

        for (SecurityDO securityDO : securityDOList) {
            if (pathMatcher.match(securityDO.getUrlPattern(), requestUrl)) {
                hasMatchDataBaseUrlPattern = true;
                LOGGER.debug("匹配到数据库中的接口{}", securityDO);
                List<PermissionDO> onePermissionDOList = permissionSecurityService.selectPermissionListBySecurityId(securityDO.getId());
                permissionDOList.addAll(onePermissionDOList);
            }
        }

        if (!hasMatchDataBaseUrlPattern) {
            LOGGER.debug("后台接口没有纳入数据库管理: hasMatchDataBaseUrlPattern[{}]", hasMatchDataBaseUrlPattern);
            throw new InternalAuthenticationServiceException("后台接口没有纳入数据库管理[" + requestUrl + "]");
        }

        LOGGER.debug("匹配到数据库中的权限组：{}", permissionDOList);

        // 返回所有地址需要的权限组
        List<String> permissionNameList = permissionDOList.stream().map(PermissionDO::getName).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(permissionNameList)) {
            LOGGER.debug("数据库没有配置该地址的权限组");
            throw new InternalAuthenticationServiceException("数据库没有配置该地址的权限组" + requestUrl);
        }

        String[] permissionNameArray = permissionNameList.toArray(new String[0]);
        return SecurityConfig.createList(permissionNameArray);//返回请求地址所需的权限组
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //是否支持该方法，返回true即可
        return true;
    }
}