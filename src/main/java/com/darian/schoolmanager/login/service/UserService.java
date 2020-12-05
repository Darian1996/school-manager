package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.login.DO.UserDO;
import com.darian.schoolmanager.login.DTO.request.UserAddOrUpdateRequest;
import com.darian.schoolmanager.login.DTO.request.UserAdminInitUserRequest;
import com.darian.schoolmanager.login.DTO.request.UserGetAllRequest;
import com.darian.schoolmanager.login.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  1:01
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserDO>
        implements IService<UserDO> {

    /**
     * 密码加密算法
     */
    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    public UserDO selectByUsername(String userName) {
        LambdaQueryWrapper<UserDO> userDOLambdaQueryWrapper = Wrappers.lambdaQuery();
        userDOLambdaQueryWrapper.eq(UserDO::getUsername, userName);
        return getBaseMapper().selectOne(userDOLambdaQueryWrapper);
    }

    /**
     * 搜索条件可可以拼接
     *
     * @param request
     * @return
     */
    public IPage<UserDO> getAllPage(UserGetAllRequest request) {
        // TODO  Params search
        IPage<UserDO> searchPage = new Page(request.getPage(), request.getLimit());
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(request.getId())) {
            queryWrapper.eq(UserDO::getId, request.getId());
        }
        if (StringUtils.hasText(request.getUsername())) {
            queryWrapper.eq(UserDO::getUsername, request.getUsername());
        }
        if (StringUtils.hasText(request.getActualName())) {
            queryWrapper.eq(UserDO::getActualName, request.getActualName());
        }
        queryWrapper.orderByAsc(UserDO::getIsDeleted);
        queryWrapper.orderByAsc(UserDO::getId);
        IPage<UserDO> userDOIPage = getBaseMapper().selectPage(searchPage, queryWrapper);
        for (UserDO userDO : userDOIPage.getRecords()) {
            userDO.setPassword(null);
        }
        return userDOIPage;
    }

    public void disable(Long id) {
        UserDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        UserDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    @Transactional(rollbackFor = Exception.class)
    public void adminInitUser(UserAdminInitUserRequest request) {
        UserDO userDO = new UserDO();
        userDO.setUsername(request.getUsername());
        userDO.setActualName(request.getActualName());
        // TODO: 默认密码放在配置信息里边
        userDO.setPassword(passwordEncoder.encode("123456"));
        save(userDO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(UserAddOrUpdateRequest request) {
        // 未开发 新增功能
        AssertUtils.assertTrue(Objects.nonNull(request.getId()), "Id不能为空");
        UserDO userDO = getById(request.getId());
        userDO.setUsername(request.getUsername());
        userDO.setActualName(request.getActualName());
        updateById(userDO);
    }

    public Map<Long, UserDO> getMapByUserIdList(List<Long> userIdList) {
        return getListByUserIdList(userIdList)
                .stream()
                .collect(Collectors.toMap(UserDO::getId, t -> t));
    }

    public List<UserDO> getListByUserIdList(List<Long> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return new ArrayList<>();
        }
        return getBaseMapper().selectBatchIds(userIdList);
    }
}
