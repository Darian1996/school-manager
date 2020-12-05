package com.darian.schoolmanager.rangecourse.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/12  2:00
 */
public class RangeCourseTaskEnum {

    @Getter
    @AllArgsConstructor
    public enum RangeCourseTaskStatusEnums {

        INIT("INIT", "初始化"),

        FAIL("FAIL", "任务失败"),

        SUCCESS("SUCCESS", "任务成功")
        ;

        private String code;

        private String desc;
    }
}
