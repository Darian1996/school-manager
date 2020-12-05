package com.darian.schoolmanager.login.DTO.request;

import lombok.Data;

import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/23  0:40
 */
@Data
public class RoleAddOrUpdateRequest {

    private Long id;

    private String name;

    private String description;

    private List<Long> permissionIdList;

    private List<Long> menuIdList;
}
