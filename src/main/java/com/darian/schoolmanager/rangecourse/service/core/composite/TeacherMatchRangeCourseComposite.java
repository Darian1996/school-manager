package com.darian.schoolmanager.rangecourse.service.core.composite;

import com.darian.schoolmanager.rangecourse.service.core.processor.teacher.TeacherMatchRangeCourseProcessor;
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
 * @date 2020/10/1  13:25
 */
@Service
public class TeacherMatchRangeCourseComposite extends AbstractMatchRangeCourseComposite {

    private static Logger LOGGER = LoggerFactory.getLogger(SchoolMatchRangeCourseComposite.class);


    private List<TeacherMatchRangeCourseProcessor> teacherMatchRangeCourseProcessorList = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, TeacherMatchRangeCourseProcessor> beansMap =
                applicationContext.getBeansOfType(TeacherMatchRangeCourseProcessor.class);
        teacherMatchRangeCourseProcessorList = beansMap
                .values()
                .stream()
                .sorted(Comparator.comparingInt(TeacherMatchRangeCourseProcessor::getOrder))
                .collect(Collectors.toList());

        LOGGER.info("teacherMatchRangeCourseProcessorList:" + teacherMatchRangeCourseProcessorList);

    }

    @Override
    public List getAllProcessorList() {
        return teacherMatchRangeCourseProcessorList;
    }
}
