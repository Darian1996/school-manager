package com.darian.schoolmanager.school.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.school.DO.SchoolDO;
import com.darian.schoolmanager.school.DTO.SchoolDTO;
import com.darian.schoolmanager.school.mapper.SchoolMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;


/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/26  23:55
 */
@Service
public class SchoolService extends ServiceImpl<SchoolMapper, SchoolDO>
        implements IService<SchoolDO> {

    public SchoolDO getOne() {
        List<SchoolDO> dataBaseSchoolDOList = getList();
        if (Objects.nonNull(dataBaseSchoolDOList) && dataBaseSchoolDOList.size() > 1) {
            AssertUtils.throwError("数据库中存在多个校园数据，请联系管理员订正");
        }
        if (CollectionUtils.isEmpty(dataBaseSchoolDOList)) {
            return null;
        }
        return dataBaseSchoolDOList.get(0);
    }

    private List<SchoolDO> getList() {
        return getBaseMapper().selectList(Wrappers.emptyWrapper());
    }

    @Transactional
    public void addOrUpdate(SchoolDTO schoolDTO) {
        SchoolDO schoolDO = convert(schoolDTO);

        SchoolDO dataBaseSchoolDO = getOne();

        // 参数 id 为空， 数据库存在学校数据，报错！！！
        if (Objects.isNull(schoolDO.getId())) {
            if (Objects.nonNull(dataBaseSchoolDO)) {
                AssertUtils.throwError("已经存在校园数据不能添加");
            }
        }

        Long requestSchoolDOId = schoolDO.getId();

        // 学校数据不为空，那么两个id 必须相同
        if (Objects.nonNull(dataBaseSchoolDO)) {
            if (!requestSchoolDOId.equals(dataBaseSchoolDO.getId())) {
                // 不相等，报错
                AssertUtils.throwError("传入参数的校园Id必须和数据库中的一致");
            }
        }

        saveOrUpdate(schoolDO);
    }

    public static SchoolDO convert(SchoolDTO schoolDTO) {
        if (Objects.isNull(schoolDTO)) {
            return null;
        }
        SchoolDO schoolDO = new SchoolDO();
        schoolDO.setId(schoolDTO.getId());
        schoolDO.setName(schoolDTO.getName());
        schoolDO.setDescription(schoolDTO.getDescription());

        return schoolDO;
    }
}
