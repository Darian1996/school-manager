package com.darian.schoolmanager.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.teacher.DO.ClassesDO;
import com.darian.schoolmanager.teacher.DO.GradeDO;
import com.darian.schoolmanager.teacher.DTO.ClassesDTO;
import com.darian.schoolmanager.teacher.mapper.ClassesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
 * @date 2020/9/27  0:39
 */
@Service
public class ClassesService extends ServiceImpl<ClassesMapper, ClassesDO>
        implements IService<ClassesDO> {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private GradeService gradeService;

    public Map<Long, ClassesDTO> getDTOMapByClassIdList(List<Long> classesIdList) {
        Map<Long, ClassesDO> classesDOMap = getMapByClassIdList(classesIdList);
        Map<Long, ClassesDTO> classesDTOMap = new HashMap<>();
        Map<Long, GradeDO> gradeDOMap = gradeService.getAllMap();
        for (Map.Entry<Long, ClassesDO> longClassesDOEntry : classesDOMap.entrySet()) {
            Long id = longClassesDOEntry.getKey();
            ClassesDO classesDO = longClassesDOEntry.getValue();
            ClassesDTO classesDTO = convert(classesDO);
            GradeDO gradeDO = gradeDOMap.get(classesDO.getGradeId());
            if (Objects.nonNull(gradeDO)) {
                classesDTO.setGradeName(gradeDO.getName());
            } else {
                LOGGER.error("getDTOMapByClassIdList: id[{}], gradeId[{}]", id, classesDO.getGradeId());
            }
            classesDTOMap.put(id, classesDTO);
        }
        return classesDTOMap;
    }

    public Map<Long, ClassesDO> getMapByClassIdList(List<Long> classesIdList) {
        if (CollectionUtils.isEmpty(classesIdList)) {
            return new HashMap<>();
        }
        List<ClassesDO> classesDOList = getListByClassIdList(classesIdList);
        return classesDOList.stream().collect(Collectors.toMap(ClassesDO::getId, t -> t));
    }

    public List<ClassesDO> getListByClassIdList(List<Long> classesIdList) {
        if (CollectionUtils.isEmpty(classesIdList)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<ClassesDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(ClassesDO::getId, classesIdList);
        return getBaseMapper().selectList(queryWrapper);
    }


    public List<ClassesDO> getAll() {
        LambdaQueryWrapper<ClassesDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(ClassesDO::getIsDeleted);
        queryWrapper.orderByDesc(ClassesDO::getGradeId);
        queryWrapper.orderByDesc(ClassesDO::getClassesNumber);
        return getBaseMapper().selectList(queryWrapper);
    }

    public void addOrUpdate(ClassesDTO classesDTO) {
        ClassesDO classesDO = convert(classesDTO);
        saveOrUpdate(classesDO);
    }

    public static ClassesDO convert(ClassesDTO classesDTO) {
        if (Objects.isNull(classesDTO)) {
            return null;
        }
        ClassesDO classesDO = new ClassesDO();
        BeanUtils.copyProperties(classesDTO, classesDO);
        return classesDO;
    }

    public void disable(Long id) {
        ClassesDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        ClassesDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    public Map<Long, ClassesDTO> getAllNotDeleteClassesDTOMap() {
        List<ClassesDTO> classesDTOList = getAllDTO();
        return classesDTOList.stream()
                .filter(Objects::nonNull)
                .filter(t -> DeleteEnums.NOT_DELETE.getCode() == t.getIsDeleted())
                .collect(Collectors.toMap(ClassesDTO::getId, t -> t));

    }

    public List<ClassesDTO> getAllDTO() {
        List<ClassesDO> classesDOList = getAll();

        if (CollectionUtils.isEmpty(classesDOList)) {
            return new ArrayList<>();
        }

        Map<Long, GradeDO> gradeDOAllMap = gradeService.getAllMap();


        List<ClassesDTO> classesDTOList = new ArrayList<>();
        for (ClassesDO classesDO : classesDOList) {
            ClassesDTO classesDTO = convert(classesDO);

            GradeDO gradeDO = gradeDOAllMap.get(classesDTO.getGradeId());
            if (Objects.nonNull(gradeDO)) {
                classesDTO.setGradeName(gradeDO.getName());
            }

            classesDTOList.add(classesDTO);
        }
        return classesDTOList;
    }


    public static ClassesDTO convert(ClassesDO classesDO) {
        if (Objects.isNull(classesDO)) {
            return null;
        }
        ClassesDTO classesDTO = new ClassesDTO();
        BeanUtils.copyProperties(classesDO, classesDTO);
        return classesDTO;
    }
}
