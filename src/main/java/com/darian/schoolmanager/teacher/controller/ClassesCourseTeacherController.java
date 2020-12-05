package com.darian.schoolmanager.teacher.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.teacher.DTO.ClassesCourseTeacherDTO;
import com.darian.schoolmanager.teacher.DTO.request.ClassesCourseTeacherGetAllSearchRequest;
import com.darian.schoolmanager.teacher.service.ClassesCourseTeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/10  21:38
 */
@RestController
public class ClassesCourseTeacherController {

    @Resource
    private ClassesCourseTeacherService classesCourseTeacherService;

    @GetMapping("/classesCourseTeacher/getAllDTOPage")
    public CustomerResponse getAllDTOPage(ClassesCourseTeacherGetAllSearchRequest request) {
        request.validator();
        IPage<ClassesCourseTeacherDTO> page = classesCourseTeacherService.getAllDTOPage(request);
        return CustomerResponse.okPage(page.getRecords(), page.getTotal());
    }

    @PostMapping("/classesCourseTeacher/addOrUpdate")
    public CustomerResponse addOrUpdate(ClassesCourseTeacherDTO dto) {
        AssertUtils.assertTrue(Objects.nonNull(dto), "传入参数不能为空");
        AssertUtils.assertTrue(Objects.nonNull(dto.getClassesId()), "班级Id不能为空");
        AssertUtils.assertTrue(Objects.nonNull(dto.getCourseId()), "课程Id不能为空");
        AssertUtils.assertTrue(Objects.nonNull(dto.getTeacherId()), "老师Id不能为空");
        classesCourseTeacherService.addOrUpdate(dto);
        return CustomerResponse.okNull();
    }


    @PostMapping("/classesCourseTeacher/enable")
    public CustomerResponse enable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        classesCourseTeacherService.enable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/classesCourseTeacher/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        classesCourseTeacherService.disable(id);
        return CustomerResponse.okNull();
    }
}
