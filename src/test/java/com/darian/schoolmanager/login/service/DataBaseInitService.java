package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.common.utils.ClassPathFileReadUtils;
import com.darian.schoolmanager.common.utils.JSONUtils;
import com.darian.schoolmanager.login.DO.UserDO;
import com.darian.schoolmanager.login.mapper.UserMapper;
import com.darian.schoolmanager.teacher.DO.ClassesCourseTeacherDO;
import com.darian.schoolmanager.teacher.DO.ClassesDO;
import com.darian.schoolmanager.teacher.DO.CourseDO;
import com.darian.schoolmanager.teacher.mapper.ClassesCourseTeacherMapper;
import com.darian.schoolmanager.teacher.mapper.ClassesMapper;
import com.darian.schoolmanager.teacher.mapper.CourseMapper;
import com.darian.schoolmanager.teacher.mapper.GradeCourseMapper;
import com.darian.schoolmanager.teacher.mapper.GradeMapper;
import lombok.Data;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBaseInitService {

    @Resource
    private GradeCourseMapper gradeCourseMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private GradeMapper gradeMapper;

    @Resource
    private ClassesMapper classesMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ClassesCourseTeacherMapper classesCourseTeacherMapper;

//    @Test
//    public void count() {
//        Long[] gradeIdList = new Long[]{7L, 8L, 9L};
//        for (Long gradeId : gradeIdList) {
//            LambdaQueryWrapper<GradeCourseDO> gradeCourseDOLambdaQueryWrapper = Wrappers.lambdaQuery();
//            gradeCourseDOLambdaQueryWrapper.eq(GradeCourseDO::getGradeId, gradeId);
//            List<GradeCourseDO> gradeCourseDOList = gradeCourseMapper.selectList(gradeCourseDOLambdaQueryWrapper);
//            long count = gradeCourseDOList.stream()
//                    .mapToInt(GradeCourseDO::getCourseCount)
//                    .sum();
//            System.out.println(count);
//        }
//
//    }

    //    @Test
    public void init() {
        String classJsonString = ClassPathFileReadUtils.readClassPathResource("class.json");
        Map<String, ClassModuleDO> classDOMap = JSONUtils.jsonToMap(classJsonString, String.class, ClassModuleDO.class);
        for (Entry<String, ClassModuleDO> stringClassModuleDOEntry : classDOMap.entrySet()) {
            ClassModuleDO classModuleDO = stringClassModuleDOEntry.getValue();
            int gradeNum = classModuleDO.getGradeNum();
            int classNum = classModuleDO.getClassNum();
            for (Entry<String, List<Integer>> stringListEntry : classModuleDO.getCourseTeacherIdMap().entrySet()) {
                String courseCode = stringListEntry.getKey();
                List<Integer> teacherIdList = stringListEntry.getValue();
                for (Integer teacherId : teacherIdList) {
                    ClassesDO classDO = getClassDO(gradeNum, classNum);
                    CourseDO courseDO = getCourseDOByCourseCode(courseCode);
                    UserDO userDO = getUserDO(Long.valueOf(teacherId));
                    if (Objects.isNull(classDO)) {
                        AssertUtils.throwError("classDO不存在");
                    }
                    if (Objects.isNull(courseDO)) {
                        AssertUtils.throwError("courseDO不存在");
                    }
                    if (Objects.isNull(userDO)) {
                        AssertUtils.throwError("userDO不存在");
                    }

                    ClassesCourseTeacherDO classesCourseTeacherDO = new ClassesCourseTeacherDO();
                    classesCourseTeacherDO.setClassesId(classDO.getId());
                    classesCourseTeacherDO.setCourseId(courseDO.getId());
                    classesCourseTeacherDO.setTeacherId(userDO.getId());
                    classesCourseTeacherMapper.insert(classesCourseTeacherDO);
                }
            }
        }
    }

    @Data
    public static class ClassModuleDO {
        /**
         * 年级
         */
        private int gradeNum;
        /**
         * 班级号
         */
        private int classNum;

        /**
         * course.Code -> teacherId
         */
        private Map<String, List<Integer>> courseTeacherIdMap = new HashMap<>();
    }


    private CourseDO getCourseDOByCourseCode(String code) {
        LambdaQueryWrapper<CourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CourseDO::getCode, code);
        return courseMapper.selectOne(queryWrapper);
    }

    private ClassesDO getClassDO(Integer gradeNum, Integer classNum) {
        LambdaQueryWrapper<ClassesDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ClassesDO::getGradeId, gradeNum);
        queryWrapper.eq(ClassesDO::getClassesNumber, classNum);
        return classesMapper.selectOne(queryWrapper);
    }

    private UserDO getUserDO(Long userId) {
        return userMapper.selectById(userId);
    }
}