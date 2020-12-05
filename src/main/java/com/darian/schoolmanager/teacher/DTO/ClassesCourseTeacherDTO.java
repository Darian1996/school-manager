package com.darian.schoolmanager.teacher.DTO;

import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/10  22:14
 */
@Data
public class ClassesCourseTeacherDTO {

    private Long id;

    private Integer isDeleted;

    private Long classesId;

    private Long courseId;

    private Long teacherId;

    // 扩展

    private String gradeNameAndClassesName;

    private String courseName;

    private String teacherName;
}
