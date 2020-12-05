package com.darian.schoolmanager.rangecourse.service.core.processor.teacher;

import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import com.darian.schoolmanager.teacher.DO.ClassesCourseTeacherDO;
import com.darian.schoolmanager.teacher.DO.WeeklyCourseDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  15:10
 */
@Service
public class MustNotTeacherMatchRangeCourseProcessor
        implements TeacherMatchRangeCourseProcessor {


    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder) {
        RangeCourseContextHolder.OneFilter oneFilter = rangeCourseContextHolder.getOneFilter();
        Long oneFilterClassesId = oneFilter.getClassesId();
        Long oneFilterCourseId = oneFilter.getCourseId();


        List<ClassesCourseTeacherDO> classesCourseTeacherDOList = rangeCourseContextHolder.getClassesCourseTeacherDOList();

        List<ClassesCourseTeacherDO> thisFilterClassesCourseTeacherDOList = classesCourseTeacherDOList.stream()
                .filter(classesCourseTeacherDO -> Objects.equals(classesCourseTeacherDO.getClassesId(), oneFilterClassesId))
                .filter(classesCourseTeacherDO -> Objects.equals(classesCourseTeacherDO.getCourseId(), oneFilterCourseId))
                .collect(Collectors.toList());

        // 过滤这个班级这个课程的集合进行设置

        List<WeeklyCourseDO> teacherWeeklyCourseDOList = rangeCourseContextHolder.getTeacherWeeklyCourseDOList();

        // TODO
        return MatcherRangeCourseResult.success();
    }
}
