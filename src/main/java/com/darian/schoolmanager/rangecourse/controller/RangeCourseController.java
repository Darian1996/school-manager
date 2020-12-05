package com.darian.schoolmanager.rangecourse.controller;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.rangecourse.service.core.RangeCourseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  17:31
 */
@RestController
public class RangeCourseController {

    @Resource
    private RangeCourseService rangeCourseService;

    @PostMapping("/rangeCourse/rangeCourse")
    public CustomerResponse rangeCourse() {
        rangeCourseService.rangeCourseMonitor();
        return CustomerResponse.okNull();
    }
}
