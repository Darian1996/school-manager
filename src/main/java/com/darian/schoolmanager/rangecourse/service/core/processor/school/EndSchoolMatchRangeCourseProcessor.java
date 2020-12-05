package com.darian.schoolmanager.rangecourse.service.core.processor.school;

import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.rangecourse.common.RangeCourseConstant;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/***
 * 这个需要 school 的匹配判定，之后 {@link RangeCourseContextHolder.OneFilter#getCourseId() } 不能为空
 *
 * after {@link MustNotSchoolMatchRangeCourseProcessor}
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/14  20:37
 */
@Service
public class EndSchoolMatchRangeCourseProcessor implements SchoolMatchRangeCourseProcessor {

    private Logger LOGGER = LoggerFactory.getLogger(RangeCourseConstant.LOGGER_NAME);

    // 1
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder) {
        RangeCourseContextHolder.OneFilter oneFilter = rangeCourseContextHolder.getOneFilter();
        Long oneFilterCourseId = oneFilter.getCourseId();
        AssertUtils.assertTrue(Objects.nonNull(oneFilterCourseId),
                "EndSchoolMatchRangeCourseProcessor:[oneFilterCourseId不能为空]");
        return MatcherRangeCourseResult.success();
//        return MatcherRangeCourseResult.error("xxxx");
    }
}

