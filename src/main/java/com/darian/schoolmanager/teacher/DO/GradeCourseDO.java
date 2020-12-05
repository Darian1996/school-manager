package com.darian.schoolmanager.teacher.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  10:38
 */
@Data
@TableName("t_grade_course")
public class GradeCourseDO extends DataBaseModel<GradeCourseDO> {

    private Long gradeId;

    private Long courseId;

    private Integer courseCount;
}
