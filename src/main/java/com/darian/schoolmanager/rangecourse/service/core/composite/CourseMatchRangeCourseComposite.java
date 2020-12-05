package com.darian.schoolmanager.rangecourse.service.core.composite;

import com.darian.schoolmanager.rangecourse.service.core.processor.course.CourseMatchRangeCourseProcessor;
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
 * @date 2020/10/1  13:23
 */
@Service
public class CourseMatchRangeCourseComposite extends AbstractMatchRangeCourseComposite {

    private static Logger LOGGER = LoggerFactory.getLogger(CourseMatchRangeCourseComposite.class);

    private List<CourseMatchRangeCourseProcessor> courseMatchRangeCourseProcessorList = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, CourseMatchRangeCourseProcessor> beansMap =
                applicationContext.getBeansOfType(CourseMatchRangeCourseProcessor.class);
        courseMatchRangeCourseProcessorList = beansMap
                .values()
                .stream()
                .sorted(Comparator.comparingInt(CourseMatchRangeCourseProcessor::getOrder))
                .collect(Collectors.toList());
        LOGGER.info("courseMatchRangeCourseProcessorList:" + courseMatchRangeCourseProcessorList);
    }

    @Override
    public List getAllProcessorList() {
        return courseMatchRangeCourseProcessorList;
    }
}
