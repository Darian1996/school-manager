package com.darian.schoolmanager.login.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.login.DTO.RoleDTO;
import com.darian.schoolmanager.login.DTO.request.RoleAddOrUpdateRequest;
import com.darian.schoolmanager.login.service.RoleService;
import com.darian.schoolmanager.common.utils.AssertUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/role/getAll")
    public CustomerResponse getAllRole() {
        List<RoleDTO> roleDTOList = roleService.selectDTOAllRoleList();
        return CustomerResponse.okList(roleDTOList);
    }

    @PostMapping("/role/addOrUpdate")
    public CustomerResponse roleAddOrUpdate(RoleAddOrUpdateRequest request) {
        AssertUtils.assertTrue(StringUtils.hasText(request.getName()), "角色名字不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(request.getDescription()), "角色描述不能为空");
        roleService.roleAddOrUpdate(request);
        return CustomerResponse.okNull();
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @PostMapping("/role/disable")
    public CustomerResponse roleDisable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        roleService.roleDisable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/role/listDisable")
    public CustomerResponse roleListDisable(@RequestParam(value = "roleIdList[]", required = false) List<Long> roleIdList) {
        AssertUtils.assertFalse(CollectionUtils.isEmpty(roleIdList), "id集合不能为空");
        roleService.roleListDisable(roleIdList);
        return CustomerResponse.okNull();

    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @PostMapping("/role/enable")
    public CustomerResponse roleEnable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        roleService.roleEnable(id);
        return CustomerResponse.okNull();
    }

}