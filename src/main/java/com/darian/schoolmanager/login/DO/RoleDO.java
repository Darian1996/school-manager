package com.darian.schoolmanager.login.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  22:08
 */
@Data
@TableName("login_role")
public class RoleDO extends DataBaseModel<RoleDO> {

    /**
     * 角色名字
     */
    private String name;

    /**
     * 描述
     */
    private String description;

}
