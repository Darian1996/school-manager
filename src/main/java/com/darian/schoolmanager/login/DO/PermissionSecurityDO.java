package com.darian.schoolmanager.login.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  22:35
 */
@Data
@TableName("login_permission_security")
public class PermissionSecurityDO extends DataBaseModel<PermissionSecurityDO> {
    private Long permissionId;

    private Long securityId;

}
