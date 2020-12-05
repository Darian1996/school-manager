package com.darian.schoolmanager.rangecourse.service.core.module;

import com.darian.schoolmanager.common.utils.AssertUtils;
import lombok.Data;
import org.springframework.util.StringUtils;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  14:12
 */
@Data
public class MatcherRangeCourseResult {

    private MatcherRangeCourseEnum matcherRangeCourseEnum;

    private String matcherErrorMsg;

    public static MatcherRangeCourseResult success() {
        MatcherRangeCourseResult matcherRangeCourseResult = new MatcherRangeCourseResult();
        matcherRangeCourseResult.setMatcherRangeCourseEnum(MatcherRangeCourseEnum.MATCHER_SUCCESS);
        return matcherRangeCourseResult;
    }

    public static MatcherRangeCourseResult error(String matcherErrorMsg) {
        MatcherRangeCourseResult matcherRangeCourseResult = new MatcherRangeCourseResult();
        matcherRangeCourseResult.setMatcherRangeCourseEnum(MatcherRangeCourseEnum.MATCHER_ERROR);
        AssertUtils.assertTrue(StringUtils.hasText(matcherErrorMsg), "排课系统匹配规则失败必须注明原因");
        matcherRangeCourseResult.setMatcherErrorMsg(matcherErrorMsg);
        return matcherRangeCourseResult;

    }

    public static MatcherRangeCourseResult schoolMustNot() {
        MatcherRangeCourseResult matcherRangeCourseResult = new MatcherRangeCourseResult();
        matcherRangeCourseResult.setMatcherRangeCourseEnum(MatcherRangeCourseEnum.SCHOOL_MATCHER_MUST_NOT);
        return matcherRangeCourseResult;
    }
}
