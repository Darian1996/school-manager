package com.darian.schoolmanager.school.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.school.DO.SchoolDO;
import com.darian.schoolmanager.school.DTO.SchoolDTO;
import com.darian.schoolmanager.school.service.SchoolService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/26  23:55
 */
@RestController
public class SchoolController {

    @Resource
    private SchoolService schoolService;

    @GetMapping("/school/getOne")
    public CustomerResponse getOne() {
        SchoolDO schoolDO = schoolService.getOne();
        return CustomerResponse.ok(schoolDO);
    }

    @PostMapping("/school/addOrUpdate")
    public CustomerResponse addOrUpdate(SchoolDTO schoolDTO) {
        AssertUtils.assertTrue(StringUtils.hasText(schoolDTO.getName()), "学校名字不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(schoolDTO.getDescription()), "学校的描述不能为空");
        schoolService.addOrUpdate(schoolDTO);
        return CustomerResponse.okNull();
    }
}
