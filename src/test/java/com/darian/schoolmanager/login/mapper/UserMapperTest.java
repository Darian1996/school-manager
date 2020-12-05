package com.darian.schoolmanager.login.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.darian.schoolmanager.login.DO.UserDO;
import com.darian.schoolmanager.common.utils.JSONUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  1:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void test_delete() {
        UserDO userDO = userMapper.selectById(1);
        LambdaQueryWrapper<UserDO> teacherDOQueryWrapper = Wrappers.lambdaQuery();

        userMapper.selectById(1L);
        teacherDOQueryWrapper.in(UserDO::getIsDeleted, 0,1);
        userMapper.selectObjs(teacherDOQueryWrapper);
        System.out.println(userDO);

    }

    @Test
    public void test_selectList() {
        // 1 , 10
        IPage<UserDO> page = new Page(1, 10);
        QueryWrapper<UserDO> teacherDOQueryWrapper = Wrappers.query();
        IPage<UserDO> teacherDOIPage = userMapper.selectPage(page, teacherDOQueryWrapper);
        System.out.println(JSONUtils.beanToJson(teacherDOIPage));
    }

    @Test
    public void test_insert() {
        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setUsername("test_userName");
        userDO.setPassword("test_setPassword");
        userDO.setIsDeleted(0);
        System.out.println(userMapper.insert(userDO));
    }

}