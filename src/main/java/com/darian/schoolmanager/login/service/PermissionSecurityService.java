package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DO.PermissionSecurityDO;
import com.darian.schoolmanager.login.mapper.PermissionSecurityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  23:59
 */
@Service
public class PermissionSecurityService extends ServiceImpl<PermissionSecurityMapper, PermissionSecurityDO>
        implements IService<PermissionSecurityDO> {


    @Resource
    private PermissionService permissionService;

    public List<PermissionDO> selectPermissionListBySecurityId(Long securityId) {
        List<PermissionSecurityDO> permissionSecurityDOList = selectPermissionSecurityListBySecurityId(securityId);

        List<Long> permissionIdList = permissionSecurityDOList.stream()
                .map(PermissionSecurityDO::getPermissionId)
                .collect(Collectors.toList());

        return permissionService.selectListByPermissionIdList(permissionIdList);
    }


    public List<PermissionSecurityDO> selectPermissionSecurityListBySecurityId(Long securityId) {
        LambdaQueryWrapper<PermissionSecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PermissionSecurityDO::getSecurityId, securityId)
                .eq(PermissionSecurityDO::getIsDeleted, DeleteEnums.NOT_DELETE.getCode());
        return getBaseMapper().selectList(queryWrapper);
    }

    @Transactional
    public void insertList(Long permissionId, List<Long> securityIdList) {
        LambdaQueryWrapper<PermissionSecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PermissionSecurityDO::getPermissionId, permissionId);
        getBaseMapper().delete(queryWrapper);

        if (CollectionUtils.isEmpty(securityIdList)) {
            return;
        }

        List<PermissionSecurityDO> permissionSecurityServiceList = new ArrayList<>();

        for (Long securityId : securityIdList) {
            if (Objects.isNull(securityId)) {
                continue;
            }
            PermissionSecurityDO permissionSecurityDO = new PermissionSecurityDO();
            permissionSecurityDO.setPermissionId(permissionId);
            permissionSecurityDO.setSecurityId(securityId);
            permissionSecurityServiceList.add(permissionSecurityDO);
        }

        saveBatch(permissionSecurityServiceList);

    }

    public List<PermissionSecurityDO> selectByPermissionList(List<Long> permissionIdList) {
        LambdaQueryWrapper<PermissionSecurityDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(PermissionSecurityDO::getPermissionId, permissionIdList);
        return getBaseMapper().selectList(queryWrapper);
    }


}
