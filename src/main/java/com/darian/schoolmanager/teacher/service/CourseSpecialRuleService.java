package com.darian.schoolmanager.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.common.utils.JSONUtils;
import com.darian.schoolmanager.school.service.SchoolService;
import com.darian.schoolmanager.teacher.DO.CourseDO;
import com.darian.schoolmanager.teacher.DO.CourseSpecialRuleDO;
import com.darian.schoolmanager.teacher.DTO.CourseSpecialRuleDTO;
import com.darian.schoolmanager.teacher.DTO.CourseSpecialRuleEnum.CourseSpecialBizTypeEnums;
import com.darian.schoolmanager.teacher.DTO.CourseSpecialRuleEnum.CourseSpecialRuleStringEnums;
import com.darian.schoolmanager.teacher.DTO.CourseSpecialRuleEnum.TwoCourseModule;
import com.darian.schoolmanager.teacher.mapper.CourseSpecialRuleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/6  19:42
 */
@Service
public class CourseSpecialRuleService extends
        ServiceImpl<CourseSpecialRuleMapper, CourseSpecialRuleDO>
        implements IService<CourseSpecialRuleDO> {

    @Resource
    private SchoolService schoolService;

    @Resource
    private CourseService courseService;

    @Transactional
    public void addOrUpdate(CourseSpecialRuleDTO courseSpecialRuleDTO) {
        CourseSpecialRuleDO courseSpecialRuleDO = convert(courseSpecialRuleDTO);
        saveOrUpdate(courseSpecialRuleDO);
        updateOneDesc(courseSpecialRuleDO);
    }

    private static CourseSpecialRuleDO convert(CourseSpecialRuleDTO courseSpecialRuleDTO) {
        if (Objects.isNull(courseSpecialRuleDTO)) {
            return null;
        }
        CourseSpecialRuleDO courseSpecialRuleDO = new CourseSpecialRuleDO();
        validatorExtStr(courseSpecialRuleDO);

        BeanUtils.copyProperties(courseSpecialRuleDTO, courseSpecialRuleDO);
        return courseSpecialRuleDO;
    }

    private static void validatorExtStr(CourseSpecialRuleDO courseSpecialRuleDO) {
        if (CourseSpecialRuleStringEnums.HALF_DAY_CONFLICT_OTHER_COURSE.getCode()
                .equals(courseSpecialRuleDO.getRuleString())) {
            AssertUtils.assertTrue(StringUtils.hasText(courseSpecialRuleDO.getExtStr()), "extStr不能为空");
            TwoCourseModule twoCourseModule = JSONUtils.jsonToBean(courseSpecialRuleDO.getExtStr(), TwoCourseModule.class);
            AssertUtils.assertTrue(Objects.nonNull(twoCourseModule.getCourseId()), "扩展字段课程id不能为空");
        }
        if (CourseSpecialRuleStringEnums.ONE_DAY_CONFLICT_OTHER_COURSE.getCode()
                .equals(courseSpecialRuleDO.getRuleString())) {
            AssertUtils.assertTrue(StringUtils.hasText(courseSpecialRuleDO.getExtStr()), "extStr不能为空");
            TwoCourseModule twoCourseModule = JSONUtils.jsonToBean(courseSpecialRuleDO.getExtStr(), TwoCourseModule.class);
            AssertUtils.assertTrue(Objects.nonNull(twoCourseModule.getCourseId()), "扩展字段课程id不能为空");
        }
    }

    private static CourseSpecialRuleDTO convert(CourseSpecialRuleDO courseSpecialRuleDO) {
        if (Objects.isNull(courseSpecialRuleDO)) {
            return null;
        }
        CourseSpecialRuleDTO courseSpecialRuleDTO = new CourseSpecialRuleDTO();
        BeanUtils.copyProperties(courseSpecialRuleDO, courseSpecialRuleDTO);
        return courseSpecialRuleDTO;
    }

    public void disable(Long id) {
        CourseSpecialRuleDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.DELETE.getCode());
        updateById(obj);
    }

    public void enable(Long id) {
        CourseSpecialRuleDO obj = getById(id);
        obj.setIsDeleted(DeleteEnums.NOT_DELETE.getCode());
        updateById(obj);
    }

    public List<CourseSpecialRuleDTO> getAllDTO() {
        List<CourseSpecialRuleDO> courseSpecialRuleDOList = getAll();

        List<CourseSpecialRuleDTO> courseSpecialRuleDTOList = new ArrayList<>();
        for (CourseSpecialRuleDO courseSpecialRuleDO : courseSpecialRuleDOList) {
            CourseSpecialRuleDTO courseSpecialRuleDTO = convert(courseSpecialRuleDO);
            courseSpecialRuleDTOList.add(courseSpecialRuleDTO);
        }
        // TODO:

        return courseSpecialRuleDTOList;
    }

    public List<CourseSpecialRuleDO> getAll() {
        LambdaQueryWrapper<CourseSpecialRuleDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(CourseSpecialRuleDO::getIsDeleted);
        queryWrapper.orderByAsc(CourseSpecialRuleDO::getBizType);
        queryWrapper.orderByAsc(CourseSpecialRuleDO::getBizId);
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 更新数据库中的 desc 字段
     */
    public void updateDesc() {
        List<CourseSpecialRuleDO> courseSpecialRuleDOList = getAll();
        for (CourseSpecialRuleDO courseSpecialRuleDO : courseSpecialRuleDOList) {
            updateOneDesc(courseSpecialRuleDO);
        }
        return;
    }

    private void updateOneDesc(CourseSpecialRuleDO courseSpecialRuleDO) {
        String bizType = courseSpecialRuleDO.getBizType();

        if (CourseSpecialBizTypeEnums.COURSE.getBizType().equals(bizType)) {
            CourseDO courseDO = courseService.getById(courseSpecialRuleDO.getId());
            courseSpecialRuleDO.setBizIdDesc(courseDO.getName());
        }
        // TODO:  bizType

        String ruleString = courseSpecialRuleDO.getRuleString();
        courseSpecialRuleDO.setRuleStringDesc(
                CourseSpecialRuleStringEnums.getEnumByCode(ruleString).getDesc());

        updateById(courseSpecialRuleDO);
    }
}
