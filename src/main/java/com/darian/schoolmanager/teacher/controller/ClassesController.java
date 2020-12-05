package com.darian.schoolmanager.teacher.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.teacher.DTO.ClassesDTO;
import com.darian.schoolmanager.teacher.service.ClassesService;
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
 * @date 2020/9/27  0:40
 */
@RestController
public class ClassesController {

    @Resource
    private ClassesService classesService;

    @GetMapping("/classes/getAllDTO")
    public CustomerResponse getAllDTO() {
        List<ClassesDTO> classesDOList = classesService.getAllDTO();
        return CustomerResponse.okList(classesDOList);
    }

    @PostMapping("/classes/addOrUpdate")
    public CustomerResponse addOrUpdate(ClassesDTO classesDTO) {
        AssertUtils.assertTrue(Objects.nonNull(classesDTO.getGradeId()), "班级的年级序号不能为空");
        AssertUtils.assertTrue(Objects.nonNull(classesDTO.getClassesNumber()), "班级序号不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(classesDTO.getName()), "班级描述不能为空");
        classesService.addOrUpdate(classesDTO);
        return CustomerResponse.okNull();
    }

    @PostMapping("/classes/enable")
    public CustomerResponse enable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        classesService.enable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/classes/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        classesService.disable(id);
        return CustomerResponse.okNull();
    }
}
