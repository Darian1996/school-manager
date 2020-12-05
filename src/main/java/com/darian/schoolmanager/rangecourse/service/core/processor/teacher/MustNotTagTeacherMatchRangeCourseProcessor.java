package com.darian.schoolmanager.rangecourse.service.core.processor.teacher;

import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import org.springframework.stereotype.Service;

/***
 * 教师打标分组，进行 禁止上课匹配
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  15:12
 */
@Service
public class MustNotTagTeacherMatchRangeCourseProcessor
        implements TeacherMatchRangeCourseProcessor {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder) {
        // TODO:
        return MatcherRangeCourseResult.success();
    }
}
