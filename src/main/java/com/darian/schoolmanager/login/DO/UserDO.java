package com.darian.schoolmanager.login.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;
import lombok.ToString;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  0:56
 */
@Data
@TableName("login_user")
public class UserDO extends DataBaseModel<UserDO> {

    private String username;

    private String actualName;

    private String password;

    private Integer age;

    private String email;

}