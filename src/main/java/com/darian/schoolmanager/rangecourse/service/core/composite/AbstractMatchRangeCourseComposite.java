package com.darian.schoolmanager.rangecourse.service.core.composite;

import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.configuration.exception.AssertionException;
import com.darian.schoolmanager.rangecourse.common.RangeCourseConstant;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseEnum;
import com.darian.schoolmanager.rangecourse.service.core.module.MatcherRangeCourseResult;
import com.darian.schoolmanager.rangecourse.service.core.module.RangeCourseContextHolder;
import com.darian.schoolmanager.rangecourse.service.core.processor.MatchRangeCourseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  14:34
 */
public abstract class AbstractMatchRangeCourseComposite<T extends MatchRangeCourseProcessor>
        implements MatchRangeCourseComposite<T> {

    @Resource(name = "rangeCourseProcessorThreadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static Logger LOGGER = LoggerFactory.getLogger(RangeCourseConstant.LOGGER_NAME);

    @Override
    public final MatcherRangeCourseResult matcherRule(RangeCourseContextHolder rangeCourseContextHolder) {
        AssertUtils.assertTrue(!CollectionUtils.isEmpty(rangeCourseContextHolder.getAllNotDeleteClassesDTOMap()), "班级的集合为空");
        AssertUtils.assertTrue(!CollectionUtils.isEmpty(rangeCourseContextHolder.getAllNotDeleteCourseDOMap()), "课程的集合为空");

        List<T> allProcessorList = getAllProcessorList();
        String compositeName = this.getClass().getSimpleName();
        LOGGER.info("进入到组合器[{}]", compositeName);
        if (CollectionUtils.isEmpty(allProcessorList)) {
            LOGGER.warn("组合器[{}]没有处理器", compositeName);
        }
        for (T matchRangeCourseProcessor : allProcessorList) {
            String processorName = matchRangeCourseProcessor.getClass().getSimpleName();
            MatcherRangeCourseResult matcherRangeCourseResult = null;

            long start = System.currentTimeMillis();
            try {
                if (matchRangeCourseProcessor.processor(rangeCourseContextHolder)) {
                    Future<MatcherRangeCourseResult> feature = threadPoolTaskExecutor.submit(
                            matchRangeCourseProcessor.matcherRuleAsync(rangeCourseContextHolder));
                    try {
                        matcherRangeCourseResult = feature.get();
                    } catch (ExecutionException e) {
                        Throwable cause = e.getCause();
                        if (cause instanceof AssertionException) {
                            throw new AssertionException(cause.getMessage());
                        }
                        LOGGER.error("ExecutionException：", e);
                    } catch (Exception e) {
                        LOGGER.error("匹配规则线程池出现异常：", e);
                    }
                    //                    matcherRangeCourseResult = matchRangeCourseProcessor.matcherRule(rangeCourseContextHolder);
                    // 不成功，直接返回
                    AssertUtils.assertTrue(Objects.nonNull(matcherRangeCourseResult), "处理器处理结果不能为空");
                    if (!MatcherRangeCourseEnum.MATCHER_SUCCESS.equals(matcherRangeCourseResult.getMatcherRangeCourseEnum())) {
                        return matcherRangeCourseResult;
                    }
                } else {
                    LOGGER.info(String.format("[{}]处理器没有工作", processorName));
                }
            } finally {

                long end = System.currentTimeMillis();
                LOGGER.info("排课系统[{}]处理器，结果为：[{}][{}]",
                        processorName,
                        matcherRangeCourseResult,
                        end - start + "ms");
            }
        }
        // 匹配完毕，返回成功
        return MatcherRangeCourseResult.success();
    }


    @Override
    public final void initProcessor() {
        List<T> allProcessorList = getAllProcessorList();
        String compositeName = this.getClass().getSimpleName();
        LOGGER.info("进入到组合器[{}]", compositeName);
        if (CollectionUtils.isEmpty(allProcessorList)) {
            LOGGER.warn("组合器[{}]没有处理器", compositeName);
        }
        for (T matchRangeCourseProcessor : allProcessorList) {
            String processorName = matchRangeCourseProcessor.getClass().getSimpleName();
            try {
                matchRangeCourseProcessor.initProcessor();
            } catch (Exception e) {
                LOGGER.error("AbstractMatchRangeCourseComposite.initProcessor", e);
                throw e;
            } finally {
                LOGGER.info("排课系统[{}]处理器，初始化完成", processorName);
            }
        }
    }

    protected abstract List<T> getAllProcessorList();
}
