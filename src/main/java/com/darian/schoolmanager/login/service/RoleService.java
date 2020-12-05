package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DO.RoleDO;
import com.darian.schoolmanager.login.DO.RoleUserDO;
import com.darian.schoolmanager.login.DTO.MenuDTO;
import com.darian.schoolmanager.login.DTO.RoleDTO;
import com.darian.schoolmanager.login.DTO.request.RoleAddOrUpdateRequest;
import com.darian.schoolmanager.login.mapper.RoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/17  0:35
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, RoleDO>
        implements IService<RoleDO> {

    @Resource
    private RoleUserService roleUserService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RolePermissionDOService rolePermissionDOService;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleMenuService roleMenuService;

    public List<RoleDO> selectListByUserId(Long userId) {
        List<RoleUserDO> roleUserDOList = roleUserService.selectListByUserId(userId);

        List<Long> roleIdList = roleUserDOList.stream()
                .map(RoleUserDO::getRoleId)
                .collect(Collectors.toList());

        return selectListByRoleIdList(roleIdList);
    }


    public List<RoleDO> selectListByRoleIdList(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<RoleDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(RoleDO::getId, roleIdList);

        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 查询所有的角色
     *
     * @return
     */
    public List<RoleDO> selectAllRoleList() {
        LambdaQueryWrapper<RoleDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(RoleDO::getIsDeleted);
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 还查询了所有角色所具有的权限
     *
     * @return
     */
    public List<RoleDTO> selectDTOAllRoleList() {
        List<RoleDO> roleDOList = selectAllRoleList();


        List<Long> roleIdList = roleDOList.stream().map(roleDO -> roleDO.getId()).collect(Collectors.toList());

        Map<Long, List<PermissionDO>> longPermissionMap = permissionService.selectMapByRoleIdList(roleIdList);
        //

        Map<Long, List<MenuDTO>> longMenuDTOMap = menuService.selectMapByRoleIdList(roleIdList);


        List<RoleDTO> resultRoleDTOList = new ArrayList<>();
        for (RoleDO roleDO : roleDOList) {
            RoleDTO roleDTO = convert(roleDO);
            List<PermissionDO> permissionDOList = longPermissionMap.get(roleDO.getId());
            if (!CollectionUtils.isEmpty(permissionDOList)) {
                String permissionListString = permissionDOList.stream()
                        .filter(Objects::nonNull)
                        .map(PermissionDO::getName)
                        .collect(Collectors.joining(","));
                roleDTO.setPermissionDOList(permissionDOList);
                roleDTO.setPermissionListString(permissionListString);
            }
            List<MenuDTO> menuDTOList = longMenuDTOMap.get(roleDO.getId());
            if (!CollectionUtils.isEmpty(menuDTOList)) {
                String menuListString = menuDTOList.stream()
                        .filter(Objects::nonNull)
                        .map(MenuDTO::getName)
                        .collect(Collectors.joining(","));
                roleDTO.setMenuListString(menuListString);
                roleDTO.setMenuDTOList(menuDTOList);
            }
            resultRoleDTOList.add(roleDTO);
        }

        return resultRoleDTOList;
    }

    private RoleDTO convert(RoleDO roleDO) {
        RoleDTO roleDTO = new RoleDTO();
        if (Objects.isNull(roleDO)) {
            return null;
        }
        BeanUtils.copyProperties(roleDO, roleDTO);
        return roleDTO;
    }

    @Transactional
    public void roleAddOrUpdate(RoleAddOrUpdateRequest request) {
        RoleDO roleDO = convert(request);
        saveOrUpdate(roleDO);
        rolePermissionDOService.insertList(roleDO.getId(), request.getPermissionIdList());
        roleMenuService.insertList(roleDO.getId(), request.getMenuIdList());
        return;
    }

    private RoleDO convert(RoleAddOrUpdateRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }

        RoleDO roleDO = new RoleDO();
        roleDO.setId(request.getId());
        roleDO.setName(request.getName());
        roleDO.setDescription(request.getDescription());
        return roleDO;
    }

    public void roleDisable(Long id) {
        RoleDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void roleEnable(Long id) {
        RoleDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }


    public void roleListDisable(List<Long> roleIdList) {
        List<RoleDO> roleDOList = new ArrayList<>();
        for (Long roleId : roleIdList) {
            if (Objects.isNull(roleId)) {
                continue;
            }
            RoleDO roleDO = getById(roleId);
            roleDO.setIsDeleted(DeleteEnums.DELETE.getCode());
            roleDOList.add(roleDO);
        }
        updateBatchById(roleDOList);
    }
}
