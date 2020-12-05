package com.darian.schoolmanager.teacher.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  14:09
 */
@Data
@TableName("t_course_special_rule")
public class CourseSpecialRuleDO extends DataBaseModel<CourseSpecialRuleDO> {

    public String bizType;

    public Long bizId;

    public String ruleString;

    public String extStr;

    private String bizIdDesc;

    private String ruleStringDesc;

    private String extStrDesc;
}
