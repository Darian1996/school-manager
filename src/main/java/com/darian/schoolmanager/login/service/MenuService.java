package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.login.DO.MenuDO;
import com.darian.schoolmanager.login.DO.RoleMenuDO;
import com.darian.schoolmanager.login.DTO.MenuDTO;
import com.darian.schoolmanager.login.DTO.request.MenuAddOrUpdateRequest;
import com.darian.schoolmanager.login.mapper.MenuMapper;
import com.darian.schoolmanager.common.utils.AssertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/17  0:49
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, MenuDO>
        implements IService<MenuDO> {

    private static final Long SUPER_PARENT_ID = 0L;

    @Resource
    private RoleMenuService roleMenuService;

    /**
     * TODO: 添加权限校验
     *
     * @param parentId
     * @return
     */
    public List<MenuDTO> selectDTOListByParentId(Long parentId) {
        LambdaQueryWrapper<MenuDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MenuDO::getParentId, parentId);
        queryWrapper.orderByAsc(MenuDO::getOrderId);
        List<MenuDO> menuDOList = getBaseMapper().selectList(queryWrapper);

        return convertToDTOList(menuDOList);
    }

    public List<MenuDTO> selectDTOListByRoleIdList(List<Long> roleIdList) {
        return selectDTOListByRoleIdListAndParentId(roleIdList, null);
    }

    public Map<Long, List<MenuDTO>> selectMapByRoleIdList(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new HashMap<>();
        }

        List<RoleMenuDO> roleMenuDOList = roleMenuService.selectListByRoleIdList(roleIdList);

        List<Long> menuIdList = new ArrayList<>();

        Map<Long, List<RoleMenuDO>> longListMap = roleMenuDOList.stream()
                .map(rm -> {
                    menuIdList.add(rm.getMenuId());
                    return rm;
                }).collect(Collectors.groupingBy(RoleMenuDO::getRoleId));


        Map<Long, MenuDO> longMenuDOMap = selectMapByIdList(menuIdList);

        Map<Long, List<MenuDTO>> resultMenDTOMap = new HashMap<>();

        for (Map.Entry<Long, List<RoleMenuDO>> longListEntry : longListMap.entrySet()) {
            List<MenuDTO> menuDTOList = new ArrayList<>();

            Long roleId = longListEntry.getKey();
            List<RoleMenuDO> oneRoleMenuDOList = longListEntry.getValue();
            for (RoleMenuDO roleMenuDO : oneRoleMenuDOList) {
                Long menuId = roleMenuDO.getMenuId();
                MenuDO menuDO = longMenuDOMap.get(menuId);
                if (Objects.nonNull(menuDO)) {
                    MenuDTO menuDTO = convertToDTO(menuDO);
                    menuDTOList.add(menuDTO);
                }
            }
            resultMenDTOMap.put(roleId, menuDTOList);
        }
        return resultMenDTOMap;
    }

    public List<MenuDTO> selectParentDTOListByRoleIdList(List<Long> roleIdList) {
        return selectDTOListByRoleIdListAndParentId(roleIdList, SUPER_PARENT_ID);

    }

    /**
     * @param roleIdList 角色IdList
     * @param parentId   可传，可不传，不传的话，不拼接该条件
     * @return
     */
    private List<MenuDTO> selectDTOListByRoleIdListAndParentId(List<Long> roleIdList, Long parentId) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new ArrayList<>();
        }

        List<RoleMenuDO> roleMenuDOList = roleMenuService.selectListByRoleIdList(roleIdList);
        List<Long> menuIdList = roleMenuDOList.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(menuIdList)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<MenuDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(MenuDO::getId, menuIdList);
        if (Objects.nonNull(parentId)) {
            queryWrapper.eq(MenuDO::getParentId, parentId);
        }
        queryWrapper.orderByAsc(MenuDO::getOrderId);

        List<MenuDO> menuDOList = getBaseMapper().selectList(queryWrapper);
        return convertToDTOList(menuDOList);
    }

    private Map<Long, MenuDO> selectMapByIdList(List<Long> menuIdList) {
        List<MenuDO> menuDOList = selectNotDeleteListByIdList(menuIdList);
        return menuDOList.stream().collect(Collectors.toMap(MenuDO::getId, t -> t));
    }

    private List<MenuDO> selectNotDeleteListByIdList(List<Long> menuIdList) {
        if (CollectionUtils.isEmpty(menuIdList)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<MenuDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(MenuDO::getId, menuIdList);
        queryWrapper.eq(MenuDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);

    }

    private List<MenuDTO> convertToDTOList(List<MenuDO> menuDOList) {
        if (Objects.isNull(menuDOList) || menuDOList.size() == 0) {
            return new ArrayList<>();
        }

        return menuDOList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MenuDTO convertToDTO(MenuDO menuDO) {
        if (Objects.isNull(menuDO)) {
            return null;
        }
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menuDO, menuDTO);
        return menuDTO;
    }

    public List<MenuDTO> getAllDTO() {
        List<MenuDO> menuDOList = getAll();

        Map<Long, List<MenuDO>> menuMap = menuDOList.stream()
                .sorted(Comparator.comparingLong(MenuDO::getOrderId))
                .collect(Collectors.groupingBy(MenuDO::getParentId));

        List<MenuDTO> resultMenuDTOList = new ArrayList<>();

        List<MenuDO> parentMenuDOList = menuMap.get(SUPER_PARENT_ID);
        if (CollectionUtils.isEmpty(parentMenuDOList)) {
            return Collections.emptyList();
        }

        for (MenuDO parentMenuDO : parentMenuDOList) {
            resultMenuDTOList.add(convertToDTO(parentMenuDO));
            List<MenuDO> childrenList = menuMap.get(parentMenuDO.getId());
            if (CollectionUtils.isEmpty(childrenList)) {
                continue;
            }

            for (MenuDO childrenMenuDO : childrenList) {
                resultMenuDTOList.add(convertToDTO(childrenMenuDO));
            }
        }
        return resultMenuDTOList;
    }

    public List<MenuDO> getAll() {
        LambdaQueryWrapper<MenuDO> queryWrapper = Wrappers.lambdaQuery();
        return getBaseMapper().selectList(queryWrapper);
    }

    @Transactional
    public void addOrUpdate(MenuAddOrUpdateRequest request) {
        MenuDO MenuDO = convert(request);
        validatorMenuParentId(MenuDO.getParentId());
        saveOrUpdate(MenuDO);
        return;
    }

    public static MenuDO convert(MenuAddOrUpdateRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        MenuDO MenuDO = new MenuDO();
        MenuDO.setId(request.getId());
        MenuDO.setParentId(request.getParentId());
        MenuDO.setName(request.getName());
        MenuDO.setTitle(request.getTitle());
        MenuDO.setJump(request.getJump());
        MenuDO.setIcon(request.getIcon());
        MenuDO.setLastModifiedUserId(request.getLastModifiedUserId());
        MenuDO.setOrderId(request.getOrderId());
        return MenuDO;
    }

    public void disable(Long id) {
        MenuDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        MenuDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    public void listDisable(List<Long> menuIdList) {
        List<MenuDO> MenuDOList = new ArrayList<>();
        for (Long menuId : menuIdList) {
            if (Objects.isNull(menuId)) {
                continue;
            }
            MenuDO MenuDO = getById(menuId);
            MenuDO.setIsDeleted(DeleteEnums.DELETE.getCode());
            MenuDOList.add(MenuDO);
        }
        updateBatchById(MenuDOList);
    }

    private void validatorMenuParentId(Long parentId) {
        if (SUPER_PARENT_ID.equals(parentId)) {
            return;
        }
        MenuDO menuDO = getById(parentId);
        AssertUtils.assertTrue(Objects.nonNull(menuDO), "父级菜单必须存在");
        AssertUtils.assertTrue(SUPER_PARENT_ID.equals(menuDO.getParentId()), "父级菜单必须是一级菜单");
    }

    public List<MenuDO> getAllNotDelete() {
        LambdaQueryWrapper<MenuDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MenuDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);
    }
}
