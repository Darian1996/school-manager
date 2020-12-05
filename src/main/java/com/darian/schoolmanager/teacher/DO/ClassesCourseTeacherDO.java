package com.darian.schoolmanager.teacher.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/10  21:00
 */
@Data
@TableName("t_classes_course_teacher")
public class ClassesCourseTeacherDO extends DataBaseModel<ClassesCourseTeacherDO> {

    private Long classesId;

    private Long courseId;

    private Long teacherId;

}
