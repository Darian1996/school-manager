package com.darian.schoolmanager.login.service;

import com.darian.schoolmanager.login.DO.PermissionDO;
import com.darian.schoolmanager.login.DO.RoleDO;
import com.darian.schoolmanager.login.DO.UserDO;
import com.darian.schoolmanager.login.DTO.MenuDTO;
import com.darian.schoolmanager.login.DTO.UserDTO;
import com.darian.schoolmanager.login.configuration.LoginConfiguration;
import com.darian.schoolmanager.login.configuration.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  23:08
 */
@Service
public class AuthUserService implements UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private MenuService menuService;

    @Resource
    private LoginConfiguration loginConfiguration;

    /**
     * {@link UsernameNotFoundException}
     * 会被 {@link AbstractUserDetailsAuthenticationProvider} # hideUserNotFoundExceptions 隐藏，不会抛到最外层
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new InternalAuthenticationServiceException("[username]不能为空");
        }
        // TODO  ROOT 用户
        if (username.equals(loginConfiguration.getUserRoot().getUserName())) {
            return getRootUser();
        }
        UserDO userDO = userService.selectByUsername(username);
        if (Objects.isNull(userDO)) {
            /**
             * {@link WebSecurityConfig#daoAuthenticationProvider()} 之后，就可以捕捉 UsernameNotFoundException
             */
            // throw new InternalAuthenticationServiceException("[username]不能为空");
            throw new UsernameNotFoundException("user:[" + username + "]不存在!");
        }

        Long userId = userDO.getId();
        UserDTO userDTO = new UserDTO();

        // 查询用户的角色
        List<RoleDO> roleDOList = roleService.selectListByUserId(userId);
        if (CollectionUtils.isEmpty(roleDOList)) {
            LOGGER.warn("查询用户角色为空！userId[{}]，userDO[{}]", userId, userDO);
        }
        // 查询角色
        List<Long> roleIdList = roleDOList.stream().map(RoleDO::getId).collect(Collectors.toList());
        // 根据角色查询权限组
        List<PermissionDO> permissionDOList = permissionService.selectListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(permissionDOList)) {
            LOGGER.warn("查询用户权限组为空！userId[{}]，userDO[{}],roleIdList[{}]", userId, userDO, roleIdList);
        }
        // 根据角色查询菜单
        List<MenuDTO> menuDTOList = menuService.selectParentDTOListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(menuDTOList)) {
            LOGGER.warn("查询用户菜单为空！userId[{}]，userDO[{}],roleIdList[{}]", userId, userDO, roleIdList);
        }

        // TODO: 两个层级的菜单
        for (MenuDTO menuDTO : menuDTOList) {
            menuDTO.setList(menuService.selectDTOListByParentId(menuDTO.getId()));
        }

        userDTO.setUserDO(userDO);
        userDTO.setRoleDOList(roleDOList);
        userDTO.setPermissionDOList(permissionDOList);
        userDTO.setMenuDTOList(menuDTOList);
        return userDTO;
    }

    public UserDetails getRootUser() {
        UserDO userDO = new UserDO();
        userDO.setUsername(loginConfiguration.getUserRoot().getUserName());
        userDO.setPassword(loginConfiguration.getUserRoot().getPassword());

        List<RoleDO> roleDOList = roleService.selectAllRoleList();
        List<Long> roleIdList = roleDOList.stream().map(RoleDO::getId).collect(Collectors.toList());
        // TODO:
        return null;

    }
}
