package com.darian.schoolmanager.rangecourse.service.core.processor.course;

import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import com.darian.schoolmanager.teacher.DO.WeeklyCourseDO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * 这个只处理 刚加进去的那节课 是否违反禁止上课规则
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  15:02
 */
@Service
public class MustNotCourseMatchRangeCourseProcessor
        implements CourseMatchRangeCourseProcessor {


    @Override
    public MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder) {
        RangeCourseContextHolder.OneFilter oneFilter = rangeCourseContextHolder.getOneFilter();
        Integer orderId = oneFilter.getOrderId();
        Long courseId = oneFilter.getCourseId();
        String fieldName = oneFilter.getFieldName();

        List<WeeklyCourseDO> allCourseWeeklyCourseDOList = rangeCourseContextHolder.getCourseWeeklyCourseDOList();

        // 取出来某个课程的 所有配置
        List<WeeklyCourseDO> courseWeeklyCourseDOList = allCourseWeeklyCourseDOList.stream()
                .filter(weeklyCourseDO -> Objects.equals(weeklyCourseDO.getBizId(), courseId))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(courseWeeklyCourseDOList)) {
            return MatcherRangeCourseResult.success();
        }


        // 集合存在，那么下边就必须进行匹配
        Optional<WeeklyCourseDO> weeklyCourseDOOptional = courseWeeklyCourseDOList.stream()
                .filter(weeklyCourseDO -> Objects.equals(weeklyCourseDO.getOrderId(), orderId))
                .findFirst();

        if (weeklyCourseDOOptional.isPresent()) {
            WeeklyCourseDO weeklyCourseDO = weeklyCourseDOOptional.get();
            Boolean fieldValue = weeklyCourseDO.getFieldValue(fieldName);
            if (Boolean.TRUE.equals(fieldValue)) {
                return MatcherRangeCourseResult.success();
            } else {
                return MatcherRangeCourseResult.error(
                        "课程[" + courseId + "]orderId[" + orderId + "]fieldValue[" + fieldValue + "]为false");
            }
        } else {
            return MatcherRangeCourseResult.error("课程[" + courseId + "]orderId[" + orderId + "]找不到");
        }

    }
}
