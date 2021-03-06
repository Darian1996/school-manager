package com.darian.schoolmanager.login.DTO.request;

import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/10  8:06
 */
@Data
public class UserAddOrUpdateRequest {

    private Long id;

    private String username;

    private String actualName;

    private String password;
}
