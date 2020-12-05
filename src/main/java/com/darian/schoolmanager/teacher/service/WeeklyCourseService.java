package com.darian.schoolmanager.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.modle.DeleteEnums;
import com.darian.schoolmanager.teacher.DO.WeeklyCourseDO;
import com.darian.schoolmanager.teacher.DTO.WeeklyCourseDTO;
import com.darian.schoolmanager.teacher.DTO.WeeklyCourseEnum.WeeklyCourseBizTypeEnum;
import com.darian.schoolmanager.teacher.DTO.WeeklyCourseEnum.WeeklyCourseOrderIdEnum;
import com.darian.schoolmanager.teacher.mapper.WeeklyCourseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/27  23:20
 */
@Service
public class WeeklyCourseService extends ServiceImpl<WeeklyCourseMapper, WeeklyCourseDO>
        implements IService<WeeklyCourseDO> {

    public List<WeeklyCourseDO> getAll() {
        LambdaQueryWrapper<WeeklyCourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(WeeklyCourseDO::getOrderId);
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 查询学校的课表
     *
     * @return
     */
    public List<WeeklyCourseDO> schoolGetAll() {
        LambdaQueryWrapper<WeeklyCourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WeeklyCourseDO::getBizType, WeeklyCourseBizTypeEnum.SCHOOL.getBizType());
        queryWrapper.orderByAsc(WeeklyCourseDO::getOrderId);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<WeeklyCourseDO> getAllSchoolList() {
        LambdaQueryWrapper<WeeklyCourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WeeklyCourseDO::getBizType, WeeklyCourseBizTypeEnum.SCHOOL.getBizType());
        queryWrapper.eq(WeeklyCourseDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<WeeklyCourseDO> getAllCourseList() {
        LambdaQueryWrapper<WeeklyCourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WeeklyCourseDO::getBizType, WeeklyCourseBizTypeEnum.COURSE.getBizType());
        queryWrapper.eq(WeeklyCourseDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<WeeklyCourseDO> getAllTeacherList() {
        LambdaQueryWrapper<WeeklyCourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WeeklyCourseDO::getBizType, WeeklyCourseBizTypeEnum.TEACHER.getBizType());
        queryWrapper.eq(WeeklyCourseDO::getIsDeleted, DeleteEnums.NOT_DELETE);
        return getBaseMapper().selectList(queryWrapper);

    }

    @Transactional
    public List<WeeklyCourseDO> getAllByBizTypeAndBizId(String bizType, Long bizId) {
        LambdaQueryWrapper<WeeklyCourseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WeeklyCourseDO::getBizType, bizType);
        if (Objects.nonNull(bizId) && !WeeklyCourseBizTypeEnum.SCHOOL.getBizType().equals(bizType)) {
            // TODO: check bizId 合法性
            queryWrapper.eq(WeeklyCourseDO::getBizId, bizId);
        }
        queryWrapper.orderByAsc(WeeklyCourseDO::getOrderId);
        List<WeeklyCourseDO> weeklyCourseDOList = getBaseMapper().selectList(queryWrapper);

        for (WeeklyCourseOrderIdEnum weeklyCourseOrderIdEnum : WeeklyCourseOrderIdEnum.values()) {
            boolean orderIdContains = false;
            for (WeeklyCourseDO weeklyCourseDO : weeklyCourseDOList) {
                if (weeklyCourseOrderIdEnum.getOrderId().equals(weeklyCourseDO.getOrderId())) {
                    orderIdContains = true;
                    break;
                }
            }
            if (orderIdContains) {
                continue;
            }
            // orderId 不存在添加一条数据
            WeeklyCourseDO insertWeeklyCourseDO = generatorWeeklyCourseDO(bizType, bizId, weeklyCourseOrderIdEnum);
            save(insertWeeklyCourseDO);
        }

        /**
         * 再次查询
         */
        return getBaseMapper().selectList(queryWrapper);
    }

    private static WeeklyCourseDO generatorWeeklyCourseDO(
            String bizType, Long bizId, WeeklyCourseOrderIdEnum weeklyCourseOrderIdEnum) {
        WeeklyCourseDO weeklyCourseDO = new WeeklyCourseDO();
        weeklyCourseDO.setBizType(bizType);
        weeklyCourseDO.setBizId(bizId);
        weeklyCourseDO.setOrderId(weeklyCourseOrderIdEnum.getOrderId());
        weeklyCourseDO.setName(weeklyCourseOrderIdEnum.getDesc());
        weeklyCourseDO.setOne(true);
        weeklyCourseDO.setTwo(true);
        weeklyCourseDO.setThree(true);
        weeklyCourseDO.setFour(true);
        weeklyCourseDO.setFive(true);
        weeklyCourseDO.setSix(true);
        weeklyCourseDO.setSeven(true);
        //weeklyCourseDO.setEight(true);
        //weeklyCourseDO.setNine(true);
        //weeklyCourseDO.setTen(true);
        return weeklyCourseDO;
    }

    @Transactional
    public void updateDTOList(List<WeeklyCourseDTO> weeklyCourseDTOList) {
        List<WeeklyCourseDO> weeklyCourseDOList = convert(weeklyCourseDTOList);
        updateBatchById(weeklyCourseDOList);
    }

    private static List<WeeklyCourseDO> convert(List<WeeklyCourseDTO> weeklyCourseDTOList) {
        List<WeeklyCourseDO> weeklyCourseDOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(weeklyCourseDTOList)) {
            return weeklyCourseDOList;
        }
        for (WeeklyCourseDTO weeklyCourseDTO : weeklyCourseDTOList) {
            weeklyCourseDOList.add(convert(weeklyCourseDTO));
        }
        return weeklyCourseDOList;
    }

    private static WeeklyCourseDO convert(WeeklyCourseDTO weeklyCourseDTO) {
        if (Objects.isNull(weeklyCourseDTO)) {
            return null;
        }
        WeeklyCourseDO weeklyCourseDO = new WeeklyCourseDO();
        BeanUtils.copyProperties(weeklyCourseDTO, weeklyCourseDO);
        return weeklyCourseDO;
    }
}