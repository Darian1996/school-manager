package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.login.DO.RoleMenuDO;
import com.darian.schoolmanager.login.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/17  0:45
 */
@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenuDO>
        implements IService<RoleMenuDO> {

    public List<RoleMenuDO> selectListByRoleIdList(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<RoleMenuDO> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.in(RoleMenuDO::getRoleId, roleIdList);

        return getBaseMapper().selectList(queryWrapper);
    }

    public void insertList(Long roleId, List<Long> menuIdList) {
        LambdaQueryWrapper<RoleMenuDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleMenuDO::getRoleId, roleId);
        getBaseMapper().delete(queryWrapper);

        if (CollectionUtils.isEmpty(menuIdList)) {
            return;
        }

        List<RoleMenuDO> roleMenuDOList = new ArrayList<>();

        for (Long menuId : menuIdList) {
            if (Objects.isNull(menuId)) {
                continue;
            }
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setMenuId(menuId);
            roleMenuDOList.add(roleMenuDO);
        }

        saveBatch(roleMenuDOList);
    }
}
