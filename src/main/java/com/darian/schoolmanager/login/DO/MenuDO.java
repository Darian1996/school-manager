package com.darian.schoolmanager.login.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  22:17
 */
@Data
@TableName("login_menu")
public class MenuDO extends DataBaseModel<MenuDO> {

    /**
     * 父级 id
     */
    private Long parentId;

    /**
     * 菜单名(english)
     */
    private String name;

    /**
     * 菜单中文名
     */
    private String title;

    /**
     * 菜单的 icon
     */
    private String icon;

    /**
     * 跳转的 链接
     */
    private String jump;

    /**
     * 最后更新人
     */
    private Long lastModifiedUserId;

    /**
     * 排序
     */
    private Long orderId;
}
