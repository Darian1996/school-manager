package com.darian.schoolmanager.school.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/26  23:54
 */
@Data
@TableName("t_school")
public class SchoolDO extends DataBaseModel<SchoolDO> {

    private String name;

    private String description;
}
