package com.darian.schoolmanager.rangecourse.service.core.composite;

import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import com.darian.schoolmanager.rangecourse.service.core.processor.MatchRangeCourseProcessor;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  13:23
 */
public interface MatchRangeCourseComposite<T extends MatchRangeCourseProcessor>
        extends ApplicationContextAware {

    MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder);

    void initProcessor();
}
