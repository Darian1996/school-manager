package com.darian.schoolmanager.rangecourse.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/12  1:04
 */
@Data
@ToString(callSuper = true)
@TableName("t_range_course_task")
public class RangeCourseTaskDO extends DataBaseModel<RangeCourseTaskDO> {
    private String status;
}
