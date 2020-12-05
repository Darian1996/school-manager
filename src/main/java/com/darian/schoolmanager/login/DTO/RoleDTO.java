package com.darian.schoolmanager.login.DTO;

import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DO.RoleDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/20  15:15
 */
@Data
public class RoleDTO extends RoleDO {

    private String permissionListString;

    private List<PermissionDO> permissionDOList = new ArrayList<>();

    private String menuListString;

    private List<MenuDTO> menuDTOList = new ArrayList<>();

}
