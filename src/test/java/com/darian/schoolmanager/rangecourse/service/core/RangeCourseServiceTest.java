package com.darian.schoolmanager.rangecourse.service.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RangeCourseServiceTest {

    @Resource
    private RangeCourseService rangeCourseService;

    @Test
    public void rangeCourseMonitor() {
        rangeCourseService.rangeCourseMonitor();
    }
}