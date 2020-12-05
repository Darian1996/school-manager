package com.darian.schoolmanager.rangecourse.service.core.composite;

import com.darian.schoolmanager.rangecourse.service.core.processor.grade.GradeMatchRangeCourseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  13:24
 */
@Service
public class GradeMatchRangeCourseComposite extends AbstractMatchRangeCourseComposite {

    private static Logger LOGGER = LoggerFactory.getLogger(GradeMatchRangeCourseComposite.class);

    private List<GradeMatchRangeCourseProcessor> gradeMatchRangeCourseProcessorList = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, GradeMatchRangeCourseProcessor> beansMap =
                applicationContext.getBeansOfType(GradeMatchRangeCourseProcessor.class);
        gradeMatchRangeCourseProcessorList = beansMap
                .values()
                .stream()
                .sorted(Comparator.comparingInt(GradeMatchRangeCourseProcessor::getOrder))
                .collect(Collectors.toList());

        LOGGER.info("gradeMatchRangeCourseProcessorList:" + gradeMatchRangeCourseProcessorList);

    }

    @Override
    public List getAllProcessorList() {
        return gradeMatchRangeCourseProcessorList;
    }
}
