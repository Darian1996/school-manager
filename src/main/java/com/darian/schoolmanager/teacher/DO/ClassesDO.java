package com.darian.schoolmanager.teacher.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/27  0:38
 */
@Data
@TableName("t_classes")
public class ClassesDO extends DataBaseModel<ClassesDO> {

    private Long gradeId;

    private Integer classesNumber;

    private String name;
}
