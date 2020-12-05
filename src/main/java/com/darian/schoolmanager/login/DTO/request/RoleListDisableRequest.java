package com.darian.schoolmanager.login.DTO.request;

import lombok.Data;

import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/23  3:17
 */
@Data
public class RoleListDisableRequest {

    private List<Long> roleIdList;
}
