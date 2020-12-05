package com.darian.schoolmanager.login.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  22:25
 */
@Data
@TableName("login_role_permission")
public class RolePermissionDO extends DataBaseModel<RolePermissionDO> {

    private Long roleId;

    private Long permissionId;

}
