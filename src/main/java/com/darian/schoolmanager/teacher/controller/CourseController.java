package com.darian.schoolmanager.teacher.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.teacher.DO.CourseDO;
import com.darian.schoolmanager.teacher.DTO.CourseDTO;
import com.darian.schoolmanager.teacher.service.CourseService;
import org.springframework.util.StringUtils;
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
 * @date 2020/10/1  18:06
 */
@RestController
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping("/course/getAllNotDelete")
    public CustomerResponse getAllNotDelete() {
        List<CourseDO> courseDOList = courseService.getAllNotDelete();
        return CustomerResponse.okList(courseDOList);
    }


    @GetMapping("/course/getAllDTO")
    public CustomerResponse getAllDTO() {
        List<CourseDTO> courseDOList = courseService.getAllDTO();
        return CustomerResponse.okList(courseDOList);
    }

    @PostMapping("/course/addOrUpdate")
    public CustomerResponse addOrUpdate(CourseDTO courseDTO) {
        AssertUtils.assertTrue(StringUtils.hasText(courseDTO.getName()), "科目名字不能为空");
        courseService.addOrUpdate(courseDTO);
        return CustomerResponse.okNull();
    }

    @PostMapping("/course/enable")
    public CustomerResponse enable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        courseService.enable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/course/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        courseService.disable(id);
        return CustomerResponse.okNull();
    }
}
