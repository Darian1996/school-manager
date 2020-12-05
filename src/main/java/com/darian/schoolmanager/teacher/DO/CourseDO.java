package com.darian.schoolmanager.teacher.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/1  18:04
 */
@Data
@TableName("t_course")
public class CourseDO extends DataBaseModel<CourseDO> {

    private String name;

    private String code;
}
