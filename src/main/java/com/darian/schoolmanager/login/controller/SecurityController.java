package com.darian.schoolmanager.login.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.login.DO.SecurityDO;
import com.darian.schoolmanager.login.DTO.request.SecurityAddOrUpdateRequest;
import com.darian.schoolmanager.login.service.SecurityService;
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

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/23  0:38
 */
@RestController
public class SecurityController {

    @Resource
    private SecurityService securityService;

    @GetMapping("/security/getAllNotDelete")
    public CustomerResponse getAllNotDelete() {
        return CustomerResponse.ok(securityService.getAllNotDelete());
    }


    @GetMapping("/security/getAll")
    public CustomerResponse getAll() {
        List<SecurityDO> securityDOList = securityService.getAll();
        return CustomerResponse.okList(securityDOList);
    }

    @PostMapping("/security/addOrUpdate")
    public CustomerResponse addOrUpdate(SecurityAddOrUpdateRequest request) {
        AssertUtils.assertTrue(StringUtils.hasText(request.getUrlPattern()), "接口链接不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(request.getDescription()), "接口描述不能为空");
        AssertUtils.assertTrue(Objects.nonNull(request.getOrderId()), "接口排序不能为空");
        securityService.addOrUpdate(request);
        return CustomerResponse.okNull();
    }

    @PostMapping("/security/enable")
    public CustomerResponse enable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        securityService.enable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/security/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        securityService.disable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/security/listDisable")
    public CustomerResponse listDisable(@RequestParam(value = "securityIdList[]", required = false) List<Long> securityIdList) {
        AssertUtils.assertFalse(CollectionUtils.isEmpty(securityIdList), "id集合不能为空");
        securityService.listDisable(securityIdList);
        return CustomerResponse.okNull();

    }
}
