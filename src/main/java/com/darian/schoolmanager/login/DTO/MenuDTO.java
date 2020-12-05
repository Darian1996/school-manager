package com.darian.schoolmanager.login.DTO;

import com.darian.schoolmanager.login.DO.MenuDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  23:26
 */
@Data
public class MenuDTO extends MenuDO {

    private List<MenuDTO> list = new ArrayList<>();
}
