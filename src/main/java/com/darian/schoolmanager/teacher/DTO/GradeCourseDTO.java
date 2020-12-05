package com.darian.schoolmanager.teacher.DTO;

import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  10:43
 */
@Data
public class GradeCourseDTO {

    private Long id;

    private Long gradeId;

    private Long courseId;

    private Integer courseCount;

    private Integer isDeleted;

    // 拓展组装
    private String gradeName;

    private String courseName;
}
