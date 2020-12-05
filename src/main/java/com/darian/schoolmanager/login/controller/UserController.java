package com.darian.schoolmanager.login.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.login.DO.UserDO;
import com.darian.schoolmanager.login.DTO.UserDTO;
import com.darian.schoolmanager.login.DTO.request.UserAddOrUpdateRequest;
import com.darian.schoolmanager.login.DTO.request.UserAdminInitUserRequest;
import com.darian.schoolmanager.login.DTO.request.UserGetAllRequest;
import com.darian.schoolmanager.login.service.UserService;
import com.darian.schoolmanager.login.utils.LoginUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;


@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user/getCurrentLoginUser")
    public CustomerResponse getCurrentLoginUser() {
        UserDTO loginUser = LoginUtils.getLoginUser();
        UserDO userDO = loginUser.getUserDO();
        return CustomerResponse.ok(userDO);
    }

    @GetMapping("/user/getAllPage")
    public CustomerResponse getAllPage(UserGetAllRequest request) {
        request.validator();
        IPage<UserDO> page = userService.getAllPage(request);
        return CustomerResponse.okPage(page.getRecords(), page.getTotal());
    }

    @PostMapping("/user/adminInitUser")
    public CustomerResponse adminInitUser(UserAdminInitUserRequest request) {
        AssertUtils.assertTrue(StringUtils.hasText(request.getUsername()), "用户名不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(request.getActualName()), "真实姓名不能为空");
        userService.adminInitUser(request);
        return CustomerResponse.okNull();
    }

    @PostMapping("/user/addOrUpdate")
    public CustomerResponse addOrUpdate(UserAddOrUpdateRequest request) {
        AssertUtils.assertTrue(StringUtils.hasText(request.getUsername()), "用户名不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(request.getActualName()), "真实姓名不能为空");
        userService.addOrUpdate(request);
        return CustomerResponse.okNull();
    }

    @PostMapping("/user/enable")
    public CustomerResponse enable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        userService.enable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/user/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        userService.disable(id);
        return CustomerResponse.okNull();
    }
}