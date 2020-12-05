package com.darian.schoolmanager.rangecourse.service.core.processor.school;

import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.rangecourse.common.RangeCourseConstant;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import com.darian.schoolmanager.teacher.DO.WeeklyCourseDO;
import com.darian.schoolmanager.teacher.service.WeeklyCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/***
 * 这个只处理 该学校刚加进去的那节课 是否 是不上课
 *  before {@link EndSchoolMatchRangeCourseProcessor}
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  13:56
 */
@Service
public class MustNotSchoolMatchRangeCourseProcessor implements SchoolMatchRangeCourseProcessor {

    private Logger LOGGER = LoggerFactory.getLogger(RangeCourseConstant.LOGGER_NAME);

    @Resource
    private WeeklyCourseService weeklyCourseService;

    private boolean processor = true;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean processor(RangeCourseContextHolder rangeCourseContextHolder) {
        return processor;
    }

    @Override
    public void initProcessor() {
        List<WeeklyCourseDO> schoolWeeklyCourseDOList = weeklyCourseService.getAllSchoolList();
        AssertUtils.assertFalse(CollectionUtils.isEmpty(schoolWeeklyCourseDOList), "学校相关课程设置不能为空");
        // 找不到禁止的课程，就不需要走这个流程，就是不提供 processor
        boolean methodProcessor = schoolWeeklyCourseDOList.stream()
                .filter(WeeklyCourseDO::hasMustNotCourse) // 有不能上的课程
                .findAny()
                .isPresent();
        processor = methodProcessor;
    }

    @Override
    public MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder) {

        List<WeeklyCourseDO> weeklyCourseDOList = rangeCourseContextHolder.getSchoolWeeklyCourseDOList();
        LOGGER.debug("查询学校的课程禁止上课数据[{}]", weeklyCourseDOList);

        RangeCourseContextHolder.OneFilter oneFilter = rangeCourseContextHolder.getOneFilter();

        Optional<WeeklyCourseDO> optionalWeeklyCourseDO = weeklyCourseDOList.stream()
                .filter(Objects::nonNull)
                .filter(weeklyCourseDO -> {
                    // 两个 Integer equals
                    return Objects.equals(weeklyCourseDO.getOrderId(), oneFilter.getOrderId());
                })
                .findFirst();

        AssertUtils.assertTrue(optionalWeeklyCourseDO.isPresent(), "找不到对应的学校课程表记录，请先安排学校的课程记录");

        WeeklyCourseDO weeklyCourseDO = optionalWeeklyCourseDO.get();
        Boolean fieldValue = weeklyCourseDO.getFieldValue(oneFilter.getFieldName());
        if (Boolean.TRUE.equals(fieldValue)) {
            return MatcherRangeCourseResult.success();
        } else {
            return MatcherRangeCourseResult.schoolMustNot();
        }
    }

}
