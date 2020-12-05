package com.darian.schoolmanager.login.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DTO.PermissionDTO;
import com.darian.schoolmanager.login.DTO.request.PermissionAddOrUpdateRequest;
import com.darian.schoolmanager.login.service.PermissionService;
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
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @GetMapping("/permission/getAllNotDelete")
    public CustomerResponse getAllNotDelete() {
        return CustomerResponse.ok(permissionService.getAllNotDelete());
    }


    @GetMapping("/permission/getAll")
    public CustomerResponse getAll() {
        List<PermissionDO> permissionDOList = permissionService.getAll();
        return CustomerResponse.okList(permissionDOList);
    }

    @GetMapping("/permission/getAllDTO")
    public CustomerResponse getAllDTO() {
        List<PermissionDTO> permissionDOList = permissionService.getAllDTO();
        return CustomerResponse.okList(permissionDOList);
    }

    @PostMapping("/permission/addOrUpdate")
    public CustomerResponse addOrUpdate(PermissionAddOrUpdateRequest request) {
        AssertUtils.assertTrue(StringUtils.hasText(request.getName()), "权限组名字不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(request.getDescription()), "权限组描述不能为空");
        permissionService.addOrUpdate(request);
        return CustomerResponse.okNull();
    }

    @PostMapping("/permission/enable")
    public CustomerResponse enable(PermissionAddOrUpdateRequest request) {
        AssertUtils.assertFalse(Objects.isNull(request.getId()), "id不能为空");
        permissionService.enable(request.getId());
        return CustomerResponse.okNull();
    }

    @PostMapping("/permission/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        permissionService.disable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/permission/listDisable")
    public CustomerResponse listDisable(@RequestParam(value = "permissionIdList[]", required = false) List<Long> permissionIdList) {
        AssertUtils.assertFalse(CollectionUtils.isEmpty(permissionIdList), "id集合不能为空");
        permissionService.listDisable(permissionIdList);
        return CustomerResponse.okNull();
    }
}