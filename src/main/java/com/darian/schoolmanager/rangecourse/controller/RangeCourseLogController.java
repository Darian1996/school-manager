package com.darian.schoolmanager.rangecourse.controller;

import com.darian.schoolmanager.rangecourse.service.RangeCourseLogService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/12  1:41
 */
@RestController
public class RangeCourseLogController {

    @Resource
    private RangeCourseLogService rangeCourseLogService;
}
