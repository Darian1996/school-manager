package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.configuration.exception.CustomerRuntimeException;
import com.darian.schoolmanager.login.DO.PermissionSecurityDO;
import com.darian.schoolmanager.login.DO.SecurityDO;
import com.darian.schoolmanager.login.DTO.request.SecurityAddOrUpdateRequest;
import com.darian.schoolmanager.login.mapper.SecurityMapper;
import com.darian.schoolmanager.common.utils.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/17  0:17
 */
@Service
public class SecurityService extends ServiceImpl<SecurityMapper, SecurityDO>
        implements IService<SecurityDO> {

    private static Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

    private static Long SUPER_SECURITY_ID;

    private static String SUPER_URL_PATTERN = "/**";

    @Resource
    private PermissionSecurityService permissionSecurityService;

    @Resource
    private SecurityService securityService;

    @PostConstruct
    public void postConstruct() {
        LambdaQueryWrapper<SecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityDO::getUrlPattern, SUPER_URL_PATTERN);
        SecurityDO securityDO = getBaseMapper().selectOne(queryWrapper);
        if (securityDO == null) {
            throw new CustomerRuntimeException("没有初始化，超级接口管理");
        }
        SUPER_SECURITY_ID = securityDO.getId();
        LOGGER.debug("SUPER_SECURITY_ID: [{}]", SUPER_SECURITY_ID);
    }

    public List<SecurityDO> selectEnableAllAndOrdered() {
        LambdaQueryWrapper<SecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityDO::getIsDeleted, DeleteEnums.NOT_DELETE.getCode())
                // TODO:  orderBy, 倒叙，顺序
                .orderByDesc(SecurityDO::getOrderId);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<SecurityDO> selectEnableAll() {
        LambdaQueryWrapper<SecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityDO::getIsDeleted, DeleteEnums.NOT_DELETE.getCode());
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<SecurityDO> getAll() {
        LambdaQueryWrapper<SecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(SecurityDO::getIsDeleted);
        queryWrapper.orderByDesc(SecurityDO::getOrderId);
        return getBaseMapper().selectList(queryWrapper);
    }

    public void addOrUpdate(SecurityAddOrUpdateRequest request) {
        SecurityDO securityDO = convert(request);
        assertSuperUrlPattern(securityDO);
        saveOrUpdate(securityDO);
    }

    public static SecurityDO convert(SecurityAddOrUpdateRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        SecurityDO securityDO = new SecurityDO();
        securityDO.setId(request.getId());
        securityDO.setUrlPattern(request.getUrlPattern());
        securityDO.setDescription(request.getDescription());
        securityDO.setOrderId(request.getOrderId());
        return securityDO;
    }

    public void disable(Long id) {
        SecurityDO obj = getById(id);
        assertSuperUrlPattern(obj);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        SecurityDO obj = getById(id);
        assertSuperUrlPattern(obj);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    @Transactional(rollbackFor = Exception.class)
    public void listDisable(List<Long> securityIdList) {
        List<SecurityDO> securityDOList = new ArrayList<>();
        for (Long roleId : securityIdList) {
            if (Objects.isNull(roleId)) {
                continue;
            }
            SecurityDO securityDO = getById(roleId);
            assertSuperUrlPattern(securityDO);
            securityDO.setIsDeleted(DeleteEnums.DELETE.getCode());
            securityDOList.add(securityDO);
        }
        updateBatchById(securityDOList);
    }

    public static void assertSuperUrlPattern(SecurityDO securityDO) {
        AssertUtils.assertFalse(SUPER_URL_PATTERN.equals(securityDO.getUrlPattern()), "超级接口不能变更");
        AssertUtils.assertFalse(SUPER_SECURITY_ID.equals(securityDO.getId()), "超级接口不能变更");
    }

    public List<SecurityDO> getAllNotDelete() {
        LambdaQueryWrapper<SecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);
    }

    public Map<Long, List<SecurityDO>> selectByPermissionIdList(List<Long> permissionIdList) {
        List<PermissionSecurityDO> permissionSecurityDOList = permissionSecurityService.selectByPermissionList(permissionIdList);

        Map<Long, List<Long>> permissionIdSecurityIdMap = new HashMap<>();
        List<Long> securityIdList = new ArrayList<>();

        for (PermissionSecurityDO permissionSecurityDO : permissionSecurityDOList) {
            securityIdList.add(permissionSecurityDO.getSecurityId());
            List<Long> permissionSecurityIdList = permissionIdSecurityIdMap.get(permissionSecurityDO.getPermissionId());
            if (Objects.isNull(permissionSecurityIdList)) {
                permissionSecurityIdList = new ArrayList<>();
            }
            permissionSecurityIdList.add(permissionSecurityDO.getSecurityId());
            permissionIdSecurityIdMap.put(permissionSecurityDO.getPermissionId(), permissionSecurityIdList);
        }

        Map<Long, SecurityDO> longSecurityDOMap = securityService.selectMapBySecurityIdList(securityIdList);


        Map<Long, List<SecurityDO>> resultMap = new HashMap<>();

        for (Map.Entry<Long, List<Long>> longListEntry : permissionIdSecurityIdMap.entrySet()) {
            Long permissionId = longListEntry.getKey();
            List<Long> permissionIdSecurityIdList = longListEntry.getValue();
            List<SecurityDO> securityDOList = new ArrayList<>();

            for (Long aLong : permissionIdSecurityIdList) {
                SecurityDO securityDO = longSecurityDOMap.get(aLong);
                if (Objects.isNull(securityDO)) {
                    continue;
                }
                securityDOList.add(securityDO);
            }
            resultMap.put(permissionId, securityDOList);
        }

        return resultMap;

    }

    public List<SecurityDO> selectBySecurityIdList(List<Long> securityIdList) {
        LambdaQueryWrapper<SecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SecurityDO::getId, securityIdList);
        queryWrapper.eq(SecurityDO::getIsDeleted, DeleteEnums.NOT_DELETE);

        return getBaseMapper().selectList(queryWrapper);
    }

    public Map<Long, SecurityDO> selectMapBySecurityIdList(List<Long> securityIdList) {
        List<SecurityDO> securityDOList = selectBySecurityIdList(securityIdList);

        return securityDOList.stream()
                .collect(Collectors.toMap(SecurityDO::getId, t -> t));
    }

}
