package com.darian.schoolmanager.login.DTO.request;

import com.darian.schoolmanager.common.modle.BasePageRequest;
import lombok.Data;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/10  8:11
 */
@Data
public class UserGetAllRequest extends BasePageRequest {

    private Long id;

    private String username;

    private String actualName;

}
