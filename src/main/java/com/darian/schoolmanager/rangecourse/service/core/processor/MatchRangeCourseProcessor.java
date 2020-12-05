package com.darian.schoolmanager.rangecourse.service.core.processor;

import com.darian.schoolmanager.rangecourse.common.RangeCourseConstant;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  13:14
 */
public interface MatchRangeCourseProcessor {

    Logger LOGGER = LoggerFactory.getLogger(RangeCourseConstant.LOGGER_NAME);

    /**
     * <p>排序，处理器节点需要顺序，费时间的节点放在后边<p/>
     * <p>序号越小越优先， 默认 0 <p/>
     *
     * @return
     */
    default int getOrder() {
        return 0;
    }

    /**
     * 是否处理
     *
     * @param rangeCourseContextHolder
     * @return
     */
    default boolean processor(RangeCourseContextHolder rangeCourseContextHolder) {
        return true;
    }

    default void initProcessor() {

    }

    /**
     * 异步处理
     *
     * @param rangeCourseContextHolder
     * @return
     */
    default Callable<MatcherRangeCourseResult> matcherRuleAsync(RangeCourseContextHolder rangeCourseContextHolder) {
        LOGGER.debug("进入到[MatchRangeCourseProcessor#matcherRuleAsync]");
        // [main]
        return () -> {
            LOGGER.info("进入到[MatchRangeCourseProcessor#matcherRuleAsync#call]");
            return matcherRule(rangeCourseContextHolder);
        };
    }

    /**
     * 匹配规则
     *
     * @param rangeCourseContextHolder
     * @return
     */
    MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder);
}
