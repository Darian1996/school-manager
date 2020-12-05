package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.login.DO.RolePermissionDO;
import com.darian.schoolmanager.login.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RolePermissionDOService extends ServiceImpl<RolePermissionMapper, RolePermissionDO>
        implements IService<RolePermissionDO> {

    public List<RolePermissionDO> selectListByRoleIdList(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<RolePermissionDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(RolePermissionDO::getRoleId, roleIdList)
                .eq(RolePermissionDO::getIsDeleted, DeleteEnums.NOT_DELETE.getCode());
        return getBaseMapper().selectList(queryWrapper);

    }

    @Transactional
    public void insertList(Long roleId, List<Long> permissionIdList) {
        LambdaQueryWrapper<RolePermissionDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RolePermissionDO::getRoleId, roleId);
        getBaseMapper().delete(queryWrapper);

        if (CollectionUtils.isEmpty(permissionIdList)) {
            return;
        }

        List<RolePermissionDO> rolePermissionDOList = new ArrayList<>();

        for (Long permissionId : permissionIdList) {
            if (Objects.isNull(permissionId)) {
                continue;
            }
            RolePermissionDO rolePermissionDO = new RolePermissionDO();
            rolePermissionDO.setRoleId(roleId);
            rolePermissionDO.setPermissionId(permissionId);
            rolePermissionDOList.add(rolePermissionDO);
        }

        saveBatch(rolePermissionDOList);

    }
}