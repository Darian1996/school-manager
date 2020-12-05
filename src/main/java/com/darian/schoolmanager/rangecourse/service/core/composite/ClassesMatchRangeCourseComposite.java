package com.darian.schoolmanager.rangecourse.service.core.composite;

import com.darian.schoolmanager.rangecourse.service.core.processor.classes.ClassesMatchRangeCourseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  14:27
 */
@Service
public class ClassesMatchRangeCourseComposite extends AbstractMatchRangeCourseComposite {

    private static Logger LOGGER = LoggerFactory.getLogger(ClassesMatchRangeCourseComposite.class);

    private List<ClassesMatchRangeCourseProcessor> classesMatchRangeCourseProcessorList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ClassesMatchRangeCourseProcessor> beansMap =
                applicationContext.getBeansOfType(ClassesMatchRangeCourseProcessor.class);
        classesMatchRangeCourseProcessorList = beansMap
                .values()
                .stream()
                .sorted(Comparator.comparingInt(ClassesMatchRangeCourseProcessor::getOrder))
                .collect(Collectors.toList());
        LOGGER.info("classesMatchRangeCourseProcessorList:" + classesMatchRangeCourseProcessorList);
    }

    @Override
    public List getAllProcessorList() {
        return classesMatchRangeCourseProcessorList;
    }
}
