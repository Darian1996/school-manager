package com.darian.schoolmanager.teacher.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.modle.EnumToHtmlModule;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.teacher.DTO.CourseSpecialRuleDTO;
import com.darian.schoolmanager.teacher.DTO.CourseSpecialRuleEnum.CourseSpecialBizTypeEnums;
import com.darian.schoolmanager.teacher.service.CourseSpecialRuleService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.darian.schoolmanager.teacher.DTO.CourseSpecialRuleEnum.CourseSpecialRuleStringEnums;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  19:43
 */
@RestController
public class CourseSpecialRuleController {

    @Resource
    private CourseSpecialRuleService courseSpecialRuleService;

    @GetMapping("/courseSpecialRule/getAllBizType")
    public CustomerResponse getAllBizType() {
        List<EnumToHtmlModule> moduleList = Stream.of(CourseSpecialBizTypeEnums.values())
                .map(oneEnum -> {
                    EnumToHtmlModule enumToHtmlModule = new EnumToHtmlModule();
                    enumToHtmlModule.setCode(oneEnum.getBizType());
                    enumToHtmlModule.setDesc(oneEnum.getDesc());
                    return enumToHtmlModule;
                }).collect(Collectors.toList());
        return CustomerResponse.okList(moduleList);
    }

    @GetMapping("/courseSpecialRule/getAllRuleString")
    public CustomerResponse getAllRuleString() {
        List<EnumToHtmlModule> moduleList = Stream.of(CourseSpecialRuleStringEnums.values())
                .map(oneEnum -> {
                    EnumToHtmlModule enumToHtmlModule = new EnumToHtmlModule();
                    enumToHtmlModule.setCode(oneEnum.getCode());
                    enumToHtmlModule.setDesc(oneEnum.getDesc());
                    return enumToHtmlModule;
                }).collect(Collectors.toList());
        return CustomerResponse.okList(moduleList);
    }

    @GetMapping("/courseSpecialRule/getAllDTO")
    public CustomerResponse getAllDTO() {
        List<CourseSpecialRuleDTO> objList = courseSpecialRuleService.getAllDTO();
        return CustomerResponse.okList(objList);
    }

    @PostMapping("/courseSpecialRule/updateDesc")
    public CustomerResponse updateDesc() {
        courseSpecialRuleService.updateDesc();
        return CustomerResponse.okNull();
    }

    @PostMapping("/courseSpecialRule/addOrUpdate")
    public CustomerResponse addOrUpdate(CourseSpecialRuleDTO courseSpecialRuleDTO) {
        AssertUtils.assertTrue(StringUtils.hasText(courseSpecialRuleDTO.getBizType()), "bizType不能为空");
        AssertUtils.assertTrue(CourseSpecialBizTypeEnums.contains(courseSpecialRuleDTO.getBizType()), "bizType 设置非法");
        AssertUtils.assertTrue(Objects.nonNull(courseSpecialRuleDTO.getBizId()), "bizId不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(courseSpecialRuleDTO.getRuleString()), "ruleString不能为空");
        AssertUtils.assertTrue(CourseSpecialRuleStringEnums.contains(courseSpecialRuleDTO.getRuleString()), "ruleString 设置非法");
        courseSpecialRuleService.addOrUpdate(courseSpecialRuleDTO);
        return CustomerResponse.okNull();
    }

    @PostMapping("/courseSpecialRule/enable")
    public CustomerResponse enable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        courseSpecialRuleService.enable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/courseSpecialRule/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        courseSpecialRuleService.disable(id);
        return CustomerResponse.okNull();
    }

}
