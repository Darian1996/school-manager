package com.darian.schoolmanager.login.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.login.DO.RoleUserDO;
import com.darian.schoolmanager.login.mapper.RoleUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/17  0:27
 */
@Service
public class RoleUserService extends ServiceImpl<RoleUserMapper, RoleUserDO>
        implements IService<RoleUserDO> {

    public List<RoleUserDO> selectListByUserId(Long userId) {
        LambdaQueryWrapper<RoleUserDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleUserDO::getUserId, userId);

        return getBaseMapper().selectList(queryWrapper);
    }
}
