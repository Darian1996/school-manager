package com.darian.schoolmanager.rangecourse.service.core.composite;

import com.darian.schoolmanager.rangecourse.service.core.processor.school.SchoolMatchRangeCourseProcessor;
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
 * 学校排课组合器
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  13:25
 */
@Service
public class SchoolMatchRangeCourseComposite extends AbstractMatchRangeCourseComposite {

    private List<SchoolMatchRangeCourseProcessor> schoolMatchRangeCourseProcessorList = new ArrayList<>();

    private static Logger LOGGER = LoggerFactory.getLogger(SchoolMatchRangeCourseComposite.class);


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, SchoolMatchRangeCourseProcessor> beansMap =
                applicationContext.getBeansOfType(SchoolMatchRangeCourseProcessor.class);
        schoolMatchRangeCourseProcessorList = beansMap
                .values()
                .stream()
                .sorted(Comparator.comparingInt(SchoolMatchRangeCourseProcessor::getOrder))
                .collect(Collectors.toList());
        LOGGER.info("schoolMatchRangeCourseProcessorList:" + schoolMatchRangeCourseProcessorList);

    }

    @Override
    public List getAllProcessorList() {
        return schoolMatchRangeCourseProcessorList;
    }
}
