package com.darian.schoolmanager.teacher.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.teacher.DO.GradeDO;
import com.darian.schoolmanager.teacher.DTO.GradeDTO;
import com.darian.schoolmanager.teacher.service.GradeService;
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
 * @date 2020/9/26  23:59
 */
@RestController
public class GradeController {

    @Resource
    private GradeService gradeService;

    @GetMapping("/grade/getAllNotDelete")
    public CustomerResponse getAllNotDelete() {
        List<GradeDO> gradeDOList = gradeService.getAllNotDelete();
        return CustomerResponse.okList(gradeDOList);
    }

    @GetMapping("/grade/getAll")
    public CustomerResponse getAll() {
        List<GradeDO> gradeDOList = gradeService.getAll();
        return CustomerResponse.okList(gradeDOList);
    }

    @PostMapping("/grade/addOrUpdate")
    public CustomerResponse addOrUpdate(GradeDTO gradeDTO) {
        AssertUtils.assertTrue(Objects.nonNull(gradeDTO.getGradeNumber()), "年级序号不能为空");
        AssertUtils.assertTrue(StringUtils.hasText(gradeDTO.getName()), "年级描述不能为空");
        gradeService.addOrUpdate(gradeDTO);
        return CustomerResponse.okNull();
    }

    @PostMapping("/grade/enable")
    public CustomerResponse enable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        gradeService.enable(id);
        return CustomerResponse.okNull();
    }

    @PostMapping("/grade/disable")
    public CustomerResponse disable(Long id) {
        AssertUtils.assertFalse(Objects.isNull(id), "id不能为空");
        gradeService.disable(id);
        return CustomerResponse.okNull();
    }
}
