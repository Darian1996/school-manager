package com.darian.schoolmanager.login.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  22:34
 */
@Data
@TableName("login_security")
public class SecurityDO extends DataBaseModel<SecurityDO> {

    /**
     * url
     */
    private String urlPattern;

    private Long orderId;

    /**
     * 描述
     */
    private String description;

}
