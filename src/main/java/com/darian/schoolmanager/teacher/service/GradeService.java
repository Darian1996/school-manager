package com.darian.schoolmanager.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.teacher.DO.GradeDO;
import com.darian.schoolmanager.teacher.DTO.GradeDTO;
import com.darian.schoolmanager.teacher.mapper.GradeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/26  23:59
 */
@Service
public class GradeService extends ServiceImpl<GradeMapper, GradeDO>
        implements IService<GradeDO> {

    public Map<Long, GradeDO> getAllMap() {
        List<GradeDO> gradeDOList = getAll();
        if (CollectionUtils.isEmpty(gradeDOList)) {
            return new HashMap<>();
        }
        return gradeDOList.stream()
                .collect(Collectors.toMap(GradeDO::getId, t -> t));
    }

    public List<GradeDO> getAll() {
        LambdaQueryWrapper<GradeDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(GradeDO::getIsDeleted);
        queryWrapper.orderByAsc(GradeDO::getGradeNumber);
        return getBaseMapper().selectList(queryWrapper);
    }

    public void addOrUpdate(GradeDTO gradeDTO) {
        GradeDO gradeDO = convert(gradeDTO);
        saveOrUpdate(gradeDO);
    }

    public static GradeDO convert(GradeDTO gradeDTO) {
        if (Objects.isNull(gradeDTO)) {
            return null;
        }
        GradeDO gradeDO = new GradeDO();
        BeanUtils.copyProperties(gradeDTO, gradeDO);
        return gradeDO;
    }

    public void disable(Long id) {
        GradeDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        GradeDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    public List<GradeDO> getAllNotDelete() {
        LambdaQueryWrapper<GradeDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GradeDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        queryWrapper.orderByAsc(GradeDO::getGradeNumber);
        return getBaseMapper().selectList(queryWrapper);
    }
}
