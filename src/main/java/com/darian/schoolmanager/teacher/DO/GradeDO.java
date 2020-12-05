package com.darian.schoolmanager.teacher.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/26  23:57
 */
@Data
@TableName("t_grade")
public class GradeDO extends DataBaseModel<GradeDO> {

    private Integer gradeNumber;

    private String name;
}
