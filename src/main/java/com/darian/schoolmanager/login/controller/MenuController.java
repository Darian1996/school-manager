package com.darian.schoolmanager.login.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.login.DO.MenuDO;
import com.darian.schoolmanager.login.DTO.MenuDTO;
import com.darian.schoolmanager.login.DTO.UserDTO;
import com.darian.schoolmanager.login.DTO.request.MenuAddOrUpdateRequest;
import com.darian.schoolmanager.login.service.MenuService;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.login.utils.LoginUtils;
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
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/menu/getCurrentUserMenu")
    public CustomerResponse getCurrentUserMenu() {
        UserDTO loginUser = LoginUtils.getLoginUser();
        return CustomerResponse.ok(loginUser.getMenuDTOList());
    }

    @GetMapping("/menu/getAllNotDelete")
    public CustomerResponse getAllNotDelete() {
        List<MenuDO> menuDOList = menuService.getAllNotDelete();
        return CustomerResponse.okList(menuDOList);
    }

    @GetMapping("/menu/getAllDTO")
    public CustomerResponse getAllDTO() {
        List<MenuDTO> menuDTOList = menuService.getAllDTO();
        if (Objects.isNull(menuDTOList) || menuDTOList.size() == 0) {
            return CustomerResponse.error("没有一级目录, 请添加一级目录");
        }
        return CustomerResponse.okList(menuDTOList);
    }

    @PostMapping("/menu/addOrUpdate")
    public CustomerResponse addOrUpdate(MenuAddOrUpdateRequest request) {

        AssertUtils.assertTrue(Objects.nonNull(request.getParentId()), "父级id不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(request.getName()), "菜单标识不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(request.getTitle()), "菜单描述不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(request.getJump()), "跳转的链接不能为空");

        request.setLastModifiedUserId(LoginUtils.getUserId());

        AssertUtils.assertTrue(Objects.nonNull(request.getOrderId()), "排序不能为空");
        menuService.addOrUpdate(request);
        return CustomerResponse.okNull();
    }

    @PostMapping("/menu/enable")
    public CustomerResponse enable(MenuAddOrUpdateRequest request) {
        AssertUtils.assertFalse(Objects.isNull(request.getId()), "id不能为空");
        menuService.enable(request.getId());
        return CustomerResponse.okNull();
    }

    @PostMapping("/menu/disable")
    public CustomerResponse disable(MenuAddOrUpdateRequest request) {
        AssertUtils.assertFalse(Objects.isNull(request.getId()), "id不能为空");
        menuService.disable(request.getId());
        return CustomerResponse.okNull();
    }

    @PostMapping("/menu/listDisable")
    public CustomerResponse listDisable(@RequestParam(value = "menuIdList[]", required = false) List<Long> menuIdList) {
        AssertUtils.assertFalse(CollectionUtils.isEmpty(menuIdList), "id集合不能为空");
        menuService.listDisable(menuIdList);
        return CustomerResponse.okNull();
    }

}