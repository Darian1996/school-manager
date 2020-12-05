package com.darian.schoolmanager.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.teacher.DO.CourseDO;
import com.darian.schoolmanager.teacher.DTO.CourseDTO;
import com.darian.schoolmanager.teacher.mapper.CourseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  18:05
 */
@Service
public class CourseService extends ServiceImpl<CourseMapper, CourseDO>
        implements IService<CourseDO> {

    public Map<Long, CourseDO> getAllMap() {
        List<CourseDO> courseDOList = getAll();
        if (CollectionUtils.isEmpty(courseDOList)) {
            return new HashMap<>();
        }
        return courseDOList.stream()
                .collect(Collectors.toMap(CourseDO::getId, t -> t));
    }

    public List<CourseDO> getAll() {
        LambdaQueryWrapper<CourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(CourseDO::getIsDeleted);
        queryWrapper.orderByAsc(CourseDO::getId);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<CourseDTO> getAllDTO() {
        List<CourseDO> courseDOList = getAll();
        if (CollectionUtils.isEmpty(courseDOList)) {
            return new ArrayList<>();
        }


        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (CourseDO courseDO : courseDOList) {
            CourseDTO courseDTO = convert(courseDO);
            //
            courseDTOList.add(courseDTO);
        }
        return courseDTOList;
    }

    private CourseDTO convert(CourseDO courseDO) {
        if (Objects.isNull(courseDO)) {
            return null;
        }
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(courseDO, courseDTO);
        return courseDTO;

    }

    public void addOrUpdate(CourseDTO courseDTO) {
        CourseDO courseDO = convert(courseDTO);
        saveOrUpdate(courseDO);
    }

    private CourseDO convert(CourseDTO courseDTO) {
        if (Objects.isNull(courseDTO)) {
            return null;
        }
        CourseDO courseDO = new CourseDO();
        BeanUtils.copyProperties(courseDTO, courseDO);
        return courseDO;
    }


    public void disable(Long id) {
        CourseDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        CourseDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }


    public Map<Long, CourseDO> getAllNotDeleteMap() {
        return getAllNotDelete().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(CourseDO::getId, t -> t));
    }

    public List<CourseDO> getAllNotDelete() {
        LambdaQueryWrapper<CourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CourseDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        queryWrapper.orderByAsc(CourseDO::getId);
        return getBaseMapper().selectList(queryWrapper);
    }
}
