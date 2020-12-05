package com.darian.schoolmanager.login.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 * 权限组， 每个权限组对应多个 URL
 *
 * <p>name= write description=写</>
 * <p>name= read description=读</>
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  22:22
 */
@Data
@TableName("login_permission")
public class PermissionDO extends DataBaseModel<PermissionDO> {

    /**
     * 名字
     */
    private String name;

    /**
     * 描述
     */
    private String description;

}
