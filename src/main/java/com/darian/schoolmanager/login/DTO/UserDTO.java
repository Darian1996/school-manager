package com.darian.schoolmanager.login.DTO;

import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.configuration.exception.CustomerRuntimeException;
import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DO.RoleDO;
import com.darian.schoolmanager.login.DO.UserDO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  23:24
 */
@Data
public class UserDTO implements UserDetails {

    private String sessionId;

    private UserDO userDO;

    private List<RoleDO> roleDOList;

    private List<MenuDTO> menuDTOList;

    private List<PermissionDO> permissionDOList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 根据自定义逻辑来返回用户权限，如果用户权限返回空或者和拦截路径对应权限不同，验证不通过
        if (permissionDOList != null) {
            List<GrantedAuthority> list = new ArrayList<>();

            for (PermissionDO permissionDO : permissionDOList) {
                GrantedAuthority au = new SimpleGrantedAuthority(permissionDO.getName());
                list.add(au);
            }

            return list;
        }
        if (permissionDOList == null) {
            throw new CustomerRuntimeException("这个用户的权限组还没有初始化!!!");
        }
        return null;
    }

    @Override
    public String getPassword() {
        return userDO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDO.getUsername();
    }

    /**
     * 帐号是否不过期，false则验证不通过
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 帐号是否不锁定，false则验证不通过
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证是否不过期，false则验证不通过
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 该帐号是否启用，false则验证不通过
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return DeleteEnums.NOT_DELETE.getCode() == userDO.getIsDeleted();
    }
}
