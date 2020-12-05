package com.darian.schoolmanager.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.login.DO.UserDO;
import com.darian.schoolmanager.login.service.UserService;
import com.darian.schoolmanager.teacher.DO.ClassesCourseTeacherDO;
import com.darian.schoolmanager.teacher.DO.CourseDO;
import com.darian.schoolmanager.teacher.DTO.ClassesCourseTeacherDTO;
import com.darian.schoolmanager.teacher.DTO.ClassesDTO;
import com.darian.schoolmanager.teacher.DTO.request.ClassesCourseTeacherGetAllSearchRequest;
import com.darian.schoolmanager.teacher.mapper.ClassesCourseTeacherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/10  21:38
 */
@Service
public class ClassesCourseTeacherService extends
        ServiceImpl<ClassesCourseTeacherMapper, ClassesCourseTeacherDO>
        implements IService<ClassesCourseTeacherDO> {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private ClassesService classesService;

    @Resource
    private CourseService courseService;

    @Resource
    private UserService userService;

    /**
     * 全量查询，不要调用
     *
     * @return
     */
    @Deprecated
    public List<ClassesCourseTeacherDO> getALLDOListNotDelete() {
        LambdaQueryWrapper<ClassesCourseTeacherDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ClassesCourseTeacherDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);
    }

    public void disable(Long id) {
        ClassesCourseTeacherDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        ClassesCourseTeacherDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    public IPage<ClassesCourseTeacherDTO> getAllDTOPage(ClassesCourseTeacherGetAllSearchRequest request) {
        IPage<ClassesCourseTeacherDO> searchPage = new Page(request.getPage(), request.getLimit());
        LambdaQueryWrapper<ClassesCourseTeacherDO> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(request.getId())) {
            queryWrapper.eq(ClassesCourseTeacherDO::getId, request.getId());
        }
        if (Objects.nonNull(request.getClassesId())) {
            queryWrapper.eq(ClassesCourseTeacherDO::getClassesId, request.getClassesId());
        }
        if (Objects.nonNull(request.getCourseId())) {
            queryWrapper.eq(ClassesCourseTeacherDO::getCourseId, request.getCourseId());
        }
        if (Objects.nonNull(request.getTeacherId())) {
            queryWrapper.eq(ClassesCourseTeacherDO::getTeacherId, request.getTeacherId());
        }

        IPage<ClassesCourseTeacherDO> cctDOPage = getBaseMapper().selectPage(searchPage, queryWrapper);

        List<ClassesCourseTeacherDO> cctDOList = cctDOPage.getRecords();

        List<Long> classesIdList = new ArrayList<>();
        List<Long> courseIdList = new ArrayList<>();
        List<Long> teacherIdList = new ArrayList<>();
        for (ClassesCourseTeacherDO classesCourseTeacherDO : cctDOList) {
            classesIdList.add(classesCourseTeacherDO.getClassesId());
            courseIdList.add(classesCourseTeacherDO.getCourseId());
            teacherIdList.add(classesCourseTeacherDO.getTeacherId());
        }

        Map<Long, ClassesDTO> classesDTOMap = classesService.getDTOMapByClassIdList(classesIdList);
        Map<Long, CourseDO> courseDOMap = courseService.getAllMap();
        Map<Long, UserDO> userDOMap = userService.getMapByUserIdList(teacherIdList);

        List<ClassesCourseTeacherDTO> classesCourseTeacherDTOList = new ArrayList<>();
        for (ClassesCourseTeacherDO classesCourseTeacherDO : cctDOList) {
            ClassesCourseTeacherDTO classesCourseTeacherDTO = convert(classesCourseTeacherDO);
            Long classesId = classesCourseTeacherDTO.getClassesId();
            ClassesDTO classesDTO = classesDTOMap.get(classesId);
            if (Objects.nonNull(classesDTO)) {
                classesCourseTeacherDTO.setGradeNameAndClassesName(classesDTO.getGradeName() + "-" + classesDTO.getName());
            } else {
                LOGGER.error("getAllDTOPage courseNotFound :classesId[{}]", classesId);
            }

            Long courseId = classesCourseTeacherDTO.getCourseId();
            CourseDO courseDO = courseDOMap.get(courseId);
            if (Objects.nonNull(courseDO)) {
                classesCourseTeacherDTO.setCourseName(courseDO.getName());
            } else {
                LOGGER.error("getAllDTOPage courseNotFound :courseId[{}]", classesId);
            }

            Long teacherId = classesCourseTeacherDTO.getTeacherId();
            UserDO userDO = userDOMap.get(teacherId);
            if (Objects.nonNull(userDO)) {
                classesCourseTeacherDTO.setTeacherName(userDO.getActualName());
            } else {
                LOGGER.error("getAllDTOPage teacherNotFound :teacherId[{}]", teacherId);
            }
            classesCourseTeacherDTOList.add(classesCourseTeacherDTO);
        }

        Page<ClassesCourseTeacherDTO> resultPage = new Page(request.getPage(), request.getLimit());
        resultPage.setRecords(classesCourseTeacherDTOList);
        resultPage.setTotal(cctDOPage.getTotal());
        return resultPage;
    }

    public ClassesCourseTeacherDTO convert(ClassesCourseTeacherDO classesCourseTeacherDO) {
        if (Objects.isNull(classesCourseTeacherDO)) {
            return null;
        }
        ClassesCourseTeacherDTO classesCourseTeacherDTO = new ClassesCourseTeacherDTO();
        BeanUtils.copyProperties(classesCourseTeacherDO, classesCourseTeacherDTO);
        return classesCourseTeacherDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(ClassesCourseTeacherDTO dto) {
        ClassesCourseTeacherDO classesCourseTeacherDO = convert(dto);
        saveOrUpdate(classesCourseTeacherDO);
    }

    public ClassesCourseTeacherDO convert(ClassesCourseTeacherDTO classesCourseTeacherDTO) {
        if (Objects.isNull(classesCourseTeacherDTO)) {
            return null;
        }
        ClassesCourseTeacherDO classesCourseTeacherDO = new ClassesCourseTeacherDO();
        BeanUtils.copyProperties(classesCourseTeacherDTO, classesCourseTeacherDO);
        return classesCourseTeacherDO;
    }
}

