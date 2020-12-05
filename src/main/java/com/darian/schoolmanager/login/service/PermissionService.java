package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DO.RolePermissionDO;
import com.darian.schoolmanager.login.DO.SecurityDO;
import com.darian.schoolmanager.login.DTO.PermissionDTO;
import com.darian.schoolmanager.login.DTO.request.PermissionAddOrUpdateRequest;
import com.darian.schoolmanager.login.mapper.PermissionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
 * @date 2020/9/17  0:10
 */
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, PermissionDO>
        implements IService<PermissionDO> {

    @Resource
    private RolePermissionDOService rolePermissionDOService;

    @Resource
    private PermissionSecurityService permissionSecurityService;

    @Resource
    private SecurityService securityService;

    public List<PermissionDO> selectListByPermissionIdList(List<Long> permissionDOIdList) {
        if (CollectionUtils.isEmpty(permissionDOIdList)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<PermissionDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(PermissionDO::getId, permissionDOIdList)
                .eq(PermissionDO::getIsDeleted, DeleteEnums.NOT_DELETE.getCode());
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<PermissionDO> selectListByRoleIdList(List<Long> roleIdList) {

        List<RolePermissionDO> rolePermissionDOList = rolePermissionDOService.selectListByRoleIdList(roleIdList);

        List<Long> permissionIdList = rolePermissionDOList.stream().map(RolePermissionDO::getPermissionId).collect(Collectors.toList());

        return selectListByPermissionIdList(permissionIdList);
    }

    public Map<Long, List<PermissionDO>> selectMapByRoleIdList(List<Long> roleIdList) {

        List<RolePermissionDO> rolePermissionDOList = rolePermissionDOService.selectListByRoleIdList(roleIdList);

        List<Long> permissionIdList = rolePermissionDOList.stream().map(RolePermissionDO::getPermissionId).collect(Collectors.toList());

        List<PermissionDO> permissionDOList = selectListByPermissionIdList(permissionIdList);

        Map<Long, PermissionDO> permissionDOMap = permissionDOList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(PermissionDO::getId, t -> t));

        Map<Long, List<RolePermissionDO>> roleRolePermissionDOMap = rolePermissionDOList.stream().collect(
                Collectors.groupingBy(RolePermissionDO::getRoleId));

        Map<Long, List<PermissionDO>> rolePermissionMap = new HashMap<>();

        for (Map.Entry<Long, List<RolePermissionDO>> longListEntry : roleRolePermissionDOMap.entrySet()) {
            List<Long> thisRolePermissionIdList =
                    longListEntry.getValue().stream().map(RolePermissionDO::getPermissionId).collect(Collectors.toList());

            List<PermissionDO> permissionDOS = thisRolePermissionIdList.stream()
                    .map(id -> permissionDOMap.get(id))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            rolePermissionMap.put(longListEntry.getKey(), permissionDOS);
        }

        return rolePermissionMap;
    }

    public List<PermissionDO> getAllNotDelete() {
        LambdaQueryWrapper<PermissionDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PermissionDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<PermissionDO> getAll() {
        LambdaQueryWrapper<PermissionDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(PermissionDO::getIsDeleted);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<PermissionDTO> getAllDTO() {
        List<PermissionDO> permissionDOList = getAll();

        List<Long> permissionIdList = permissionDOList.stream()
                .map(PermissionDO::getId)
                .collect(Collectors.toList());

        Map<Long, List<SecurityDO>> securityMap = securityService.selectByPermissionIdList(permissionIdList);

        List<PermissionDTO> permissionDTOList = new ArrayList<>();

        for (PermissionDO permissionDO : permissionDOList) {
            PermissionDTO permissionDTO = convert(permissionDO);
            if (Objects.isNull(permissionDTO)) {
                continue;
            }
            List<SecurityDO> securityDOList = securityMap.get(permissionDTO.getId());
            if (Objects.isNull(securityDOList)) {
                securityDOList = new ArrayList<>();
            }
            String securityListString = securityDOList.stream()
                    .map(SecurityDO::getUrlPattern)
                    .collect(Collectors.joining(","));

            permissionDTO.setSecurityListString(securityListString);
            permissionDTO.setSecurityDOList(securityDOList);

            permissionDTOList.add(permissionDTO);
        }
        return permissionDTOList;
    }

    public static PermissionDTO convert(PermissionDO permissionDO) {
        if (Objects.isNull(permissionDO)) {
            return null;
        }
        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(permissionDO, permissionDTO);
        return permissionDTO;
    }

    @Transactional
    public void addOrUpdate(PermissionAddOrUpdateRequest request) {
        PermissionDO permissionDO = convert(request);
        saveOrUpdate(permissionDO);
        List<Long> securityIdList = request.getSecurityIdList();
        permissionSecurityService.insertList(permissionDO.getId(), request.getSecurityIdList());
        return;
    }

    public static PermissionDO convert(PermissionAddOrUpdateRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        PermissionDO permissionDO = new PermissionDO();
        permissionDO.setId(request.getId());
        permissionDO.setName(request.getName());
        permissionDO.setDescription(request.getDescription());
        return permissionDO;
    }

    public void disable(Long id) {
        PermissionDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        PermissionDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    public void listDisable(List<Long> permissionIdList) {
        List<PermissionDO> permissionDOList = new ArrayList<>();
        for (Long permissionId : permissionIdList) {
            if (Objects.isNull(permissionId)) {
                continue;
            }
            PermissionDO permissionDO = getById(permissionId);
            permissionDO.setIsDeleted(DeleteEnums.DELETE.getCode());
            permissionDOList.add(permissionDO);
        }
        updateBatchById(permissionDOList);
    }
}
