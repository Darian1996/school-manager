package com.darian.schoolmanager.rangecourse.service.core;

import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.rangecourse.service.core.composite.ClassesMatchRangeCourseComposite;
import com.darian.schoolmanager.rangecourse.service.core.composite.CourseMatchRangeCourseComposite;
import com.darian.schoolmanager.rangecourse.service.core.composite.GradeMatchRangeCourseComposite;
import com.darian.schoolmanager.rangecourse.service.core.composite.MatchRangeCourseComposite;
import com.darian.schoolmanager.rangecourse.service.core.composite.SchoolMatchRangeCourseComposite;
import com.darian.schoolmanager.rangecourse.service.core.composite.TeacherMatchRangeCourseComposite;
import com.darian.schoolmanager.rangecourse.service.core.exception.MatchRangeCourseException;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseEnum;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  14:24
 */
@Service
public class MatchRangeCourseChain {

    @Resource
    private SchoolMatchRangeCourseComposite schoolMatchRangeCourseComposite;

    @Resource
    private GradeMatchRangeCourseComposite gradeMatchRangeCourseComposite;

    @Resource
    private ClassesMatchRangeCourseComposite classesMatchRangeCourseComposite;

    @Resource
    private CourseMatchRangeCourseComposite courseMatchRangeCourseComposite;

    @Resource
    private TeacherMatchRangeCourseComposite teacherMatchRangeCourseComposite;

    @Resource
    private List<MatchRangeCourseComposite> matchRangeCourseCompositeList;

    public MatcherRangeCourseResult matchRule(RangeCourseContextHolder rangeCourseContextHolder) throws MatchRangeCourseException {
        MatcherRangeCourseResult matcherRangeCourseResult = schoolMatchRangeCourseComposite.matcherRule(rangeCourseContextHolder);
        if (!MatcherRangeCourseEnum.MATCHER_SUCCESS.equals(matcherRangeCourseResult.getMatcherRangeCourseEnum())) {
            return matcherRangeCourseResult;
        }

        matcherRangeCourseResult = gradeMatchRangeCourseComposite.matcherRule(rangeCourseContextHolder);
        if (!MatcherRangeCourseEnum.MATCHER_SUCCESS.equals(matcherRangeCourseResult.getMatcherRangeCourseEnum())) {
            return matcherRangeCourseResult;
        }

        matcherRangeCourseResult = classesMatchRangeCourseComposite.matcherRule(rangeCourseContextHolder);
        if (!MatcherRangeCourseEnum.MATCHER_SUCCESS.equals(matcherRangeCourseResult.getMatcherRangeCourseEnum())) {
            return matcherRangeCourseResult;
        }

        matcherRangeCourseResult = courseMatchRangeCourseComposite.matcherRule(rangeCourseContextHolder);
        if (!MatcherRangeCourseEnum.MATCHER_SUCCESS.equals(matcherRangeCourseResult.getMatcherRangeCourseEnum())) {
            return matcherRangeCourseResult;
        }
        matcherRangeCourseResult = teacherMatchRangeCourseComposite.matcherRule(rangeCourseContextHolder);
        if (!MatcherRangeCourseEnum.MATCHER_SUCCESS.equals(matcherRangeCourseResult.getMatcherRangeCourseEnum())) {
            return matcherRangeCourseResult;
        }
        // 返回成功
        return MatcherRangeCourseResult.success();
    }

    public void initProcessor() {
        AssertUtils.assertFalse(CollectionUtils.isEmpty(matchRangeCourseCompositeList), "matchRangeCourseCompositeList is Empty");
        for (MatchRangeCourseComposite matchRangeCourseComposite : matchRangeCourseCompositeList) {
            matchRangeCourseComposite.initProcessor();
        }
    }
}
