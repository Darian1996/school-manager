package com.darian.schoolmanager.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.teacher.DO.CourseDO;
import com.darian.schoolmanager.teacher.DO.GradeCourseDO;
import com.darian.schoolmanager.teacher.DO.GradeDO;
import com.darian.schoolmanager.teacher.DTO.GradeCourseDTO;
import com.darian.schoolmanager.teacher.mapper.GradeCourseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  10:40
 */
@Service
public class GradeCourseService extends ServiceImpl<GradeCourseMapper, GradeCourseDO>
        implements IService<GradeCourseDO> {

    @Resource
    private GradeService gradeService;

    @Resource
    private CourseService courseService;

    public List<GradeCourseDO> getAllNotDelete() {
        LambdaQueryWrapper<GradeCourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GradeCourseDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<GradeCourseDO> getAll() {
        LambdaQueryWrapper<GradeCourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(GradeCourseDO::getIsDeleted);
        queryWrapper.orderByDesc(GradeCourseDO::getGradeId);
        queryWrapper.orderByDesc(GradeCourseDO::getCourseId);
        return getBaseMapper().selectList(queryWrapper);
    }

    public void addOrUpdate(GradeCourseDTO gradeCourseDTO) {
        GradeCourseDO gradeCourseDO = convert(gradeCourseDTO);
        saveOrUpdate(gradeCourseDO);
    }

    public static GradeCourseDO convert(GradeCourseDTO gradeCourseDTO) {
        if (Objects.isNull(gradeCourseDTO)) {
            return null;
        }
        GradeCourseDO gradeCourseDO = new GradeCourseDO();
        BeanUtils.copyProperties(gradeCourseDTO, gradeCourseDO);
        return gradeCourseDO;
    }

    public void disable(Long id) {
        GradeCourseDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        GradeCourseDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    public List<GradeCourseDTO> getAllDTO() {
        List<GradeCourseDO> gradeCourseDOList = getAll();

        if (CollectionUtils.isEmpty(gradeCourseDOList)) {
            return new ArrayList<>();
        }

        Map<Long, GradeDO> gradeDOAllMap = gradeService.getAllMap();
        Map<Long, CourseDO> courseDOMap = courseService.getAllMap();

        List<GradeCourseDTO> gradeCourseDTOList = new ArrayList<>();
        for (GradeCourseDO gradeCourseDO : gradeCourseDOList) {
            GradeCourseDTO gradeCourseDTO = convert(gradeCourseDO);

            GradeDO gradeDO = gradeDOAllMap.get(gradeCourseDTO.getGradeId());
            if (Objects.nonNull(gradeDO)) {
                gradeCourseDTO.setGradeName(gradeDO.getName());
            }
            CourseDO courseDO = courseDOMap.get(gradeCourseDTO.getCourseId());
            if (Objects.nonNull(courseDO)) {
                gradeCourseDTO.setCourseName(courseDO.getName());
            }

            gradeCourseDTOList.add(gradeCourseDTO);
        }
        return gradeCourseDTOList;
    }


    public static GradeCourseDTO convert(GradeCourseDO gradeCourseDO) {
        if (Objects.isNull(gradeCourseDO)) {
            return null;
        }
        GradeCourseDTO gradeCourseDTO = new GradeCourseDTO();
        BeanUtils.copyProperties(gradeCourseDO, gradeCourseDTO);
        return gradeCourseDTO;
    }
}
