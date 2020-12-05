package com.darian.schoolmanager.teacher.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.common.utils.JSONUtils;
import com.darian.schoolmanager.teacher.DO.WeeklyCourseDO;
import com.darian.schoolmanager.teacher.DTO.WeeklyCourseDTO;
import com.darian.schoolmanager.teacher.DTO.WeeklyCourseEnum.WeeklyCourseBizTypeEnum;
import com.darian.schoolmanager.teacher.service.WeeklyCourseService;
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
 * @date 2020/9/27  23:20
 */
@RestController
public class WeeklyCourseController {

    @Resource
    private WeeklyCourseService weeklyCourseService;

    //@GetMapping("/weeklyCourse/school/getAll")
    //public CustomerResponse schoolGetAll() {
    //    List<WeeklyCourseDO> weeklyCourseDOList = weeklyCourseService.schoolGetAll();
    //    return CustomerResponse.okList(weeklyCourseDOList);
    //}

    @GetMapping("/weeklyCourse/getAllByBizTypeAndBizId")
    public CustomerResponse getAllByBizTypeAndBizId(@RequestParam(required = false) String bizType,
                                                    @RequestParam(required = false) Long bizId) {
        AssertUtils.assertTrue(StringUtils.hasText(bizType), "bizType不能为空");
        AssertUtils.assertTrue(WeeklyCourseBizTypeEnum.contains(bizType), "bizType非法");
        if (!WeeklyCourseBizTypeEnum.SCHOOL.getBizType().equals(bizType)) {
            // 非学校类型必须传递 bizId
            AssertUtils.assertTrue(Objects.nonNull(bizId), "bizId不能为空");
        }
        List<WeeklyCourseDO> weeklyCourseDOList = weeklyCourseService.getAllByBizTypeAndBizId(bizType, bizId);
        return CustomerResponse.okList(weeklyCourseDOList);
    }

    @PostMapping(value = "/weeklyCourse/updateDTOList")
    public CustomerResponse updateDTOList(
            @RequestParam(required = false) String data) {
        AssertUtils.assertTrue(StringUtils.hasText(data), "数据不能为空");
        List<WeeklyCourseDTO> weeklyCourseDTOList = JSONUtils.jsonToList(data, WeeklyCourseDTO.class);
        AssertUtils.assertFalse(CollectionUtils.isEmpty(weeklyCourseDTOList), "课程表集合不能为空");
        weeklyCourseService.updateDTOList(weeklyCourseDTOList);
        return CustomerResponse.okNull();
    }
}
