package com.darian.schoolmanager.rangecourse.service.core.processor.grade;

import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.rangecourse.DO.RangeCourseLogDO;
import com.darian.schoolmanager.rangecourse.service.RangeCourseLogService;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import com.darian.schoolmanager.teacher.DO.GradeCourseDO;
import com.darian.schoolmanager.teacher.DTO.ClassesDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/***
 * 年级的每门课的总数进行匹配
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  14:56
 */
@Service
public class CourseCountGradeMatchRangeCourseProcessor implements GradeMatchRangeCourseProcessor {


    @Resource
    private RangeCourseLogService rangeCourseLogService;

    @Override
    public MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder) {
        return MatcherRangeCourseResult.success();
    }

    /**
     * 预先匹配，不用再走这个规则，类似于 回溯算法中的剪枝
     *
     * @param rangeCourseContextHolder
     */
    @Deprecated
    private MatcherRangeCourseResult oldMatchRule(RangeCourseContextHolder rangeCourseContextHolder) {
        Long taskId = rangeCourseContextHolder.getRangeCourseTaskDO().getId();
        RangeCourseContextHolder.OneFilter oneFilter = rangeCourseContextHolder.getOneFilter();
        Long classesId = oneFilter.getClassesId(); // 班级
        Long courseId = oneFilter.getCourseId(); // 课程

        Map<Long, ClassesDTO> allNotDeleteClassesDTOMap = rangeCourseContextHolder.getAllNotDeleteClassesDTOMap();
        ClassesDTO classesDTO = allNotDeleteClassesDTOMap.get(classesId);
        AssertUtils.assertTrue(Objects.nonNull(classesDTO), "班级找不到");

        Long gradeId = classesDTO.getGradeId(); // 年级


        List<GradeCourseDO> allNotDeleteGradeCourseDOList = rangeCourseContextHolder.getAllNotDeleteGradeCourseDOList();
        Optional<GradeCourseDO> optionalGradeCourseDO = allNotDeleteGradeCourseDOList.stream()
                .filter(gradeCourseDO -> Objects.equals(gradeCourseDO.getGradeId(), gradeId))
                .filter(gradeCourseDO -> Objects.equals(gradeCourseDO.getCourseId(), courseId))
                .findFirst();
        if (optionalGradeCourseDO.isPresent()) {
            // 匹配到了这个年级，这个课程的总次数，那么就拿出来总次数，和课程的比较
            GradeCourseDO gradeCourseDO = optionalGradeCourseDO.get();
            Integer databaseCourseCount = gradeCourseDO.getCourseCount();
            List<RangeCourseLogDO> listByTaskIdAndClassesId = rangeCourseLogService.getListByTaskIdAndClassesId(taskId, classesId);
            int actualSum = listByTaskIdAndClassesId.stream()
                    .mapToInt(rangeCourseLogDO -> {
                        return rangeCourseLogDO.getCourseCount(courseId);
                    }).sum();
            if (actualSum > databaseCourseCount) {
                return MatcherRangeCourseResult.error(
                        "这个年级[" + gradeId + "]班级[" + classesId + "]课程[" + courseId
                                + "],数据库总数[" + databaseCourseCount + "],实际数量[" + actualSum + "]");
            }
            return MatcherRangeCourseResult.success();


        } else {
            // 没有课程
            return MatcherRangeCourseResult.error(
                    "这个年级[" + gradeId + "]班级[" + classesId + "]没有并没有课程[" + courseId + "]"
            );
        }
    }
}
