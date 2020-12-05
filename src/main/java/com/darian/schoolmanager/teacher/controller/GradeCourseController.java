package com.darian.schoolmanager.teacher.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.teacher.DTO.GradeCourseDTO;
import com.darian.schoolmanager.teacher.service.GradeCourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  10:41
 */
@RestController
public class GradeCourseController {

    @Resource
    private GradeCourseService gradeCourseService;

    @GetMapping("/gradeCourse/getAllDTO")
    public CustomerResponse getAllDTO() {
        List<GradeCourseDTO> gradeCourseDOList = gradeCourseService.getAllDTO();
        return CustomerResponse.okList(gradeCourseDOList);
    }

    @PostMapping("/gradeCourse/addOrUpdate")
    public CustomerResponse addOrUpdate(GradeCourseDTO gradeCourseDTO) {
        AssertUtils.assertTrue(Objects.nonNull(gradeCourseDTO.getGradeId()), "年级序号不能为空");
        AssertUtils.assertTrue(Objects.nonNull(gradeCourseDTO.getCourseId()), "课程序号不能为空");
        AssertUtils.assertTrue(Objects.nonNull(gradeCourseDTO.getCourseCount()), "课程节数不能为空");
        AssertUtils.assertTrue(0 != gradeCourseDTO.getCourseCount(), "课程节数不能为0");
        gradeCourseService.addOrUpdate(gradeCourseDTO);
        return CustomerResponse.okNull();
    }

    @PostMapping("/gradeCourse/enable")
    public CustomerResponse enable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        gradeCourseService.enable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/gradeCourse/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        gradeCourseService.disable(id);
        return CustomerResponse.okNull();
    }
}
