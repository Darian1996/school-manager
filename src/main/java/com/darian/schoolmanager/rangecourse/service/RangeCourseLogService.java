package com.darian.schoolmanager.rangecourse.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.rangecourse.DO.RangeCourseLogDO;
import com.darian.schoolmanager.rangecourse.mapper.RangeCourseLogMapper;
import com.darian.schoolmanager.teacher.DTO.WeeklyCourseEnum.WeeklyCourseOrderIdEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/12  1:14
 */
@Service
public class RangeCourseLogService extends ServiceImpl<RangeCourseLogMapper, RangeCourseLogDO>
        implements IService<RangeCourseLogDO> {

    public void initRangeCourseLogList(Long taskId, List<Long> classesIdList) {
        List<RangeCourseLogDO> rangeCourseLogDOList = new ArrayList<>();
        for (Long classesId : classesIdList) {
            for (WeeklyCourseOrderIdEnum weeklyCourseOrderIdEnum : WeeklyCourseOrderIdEnum.values()) {
                RangeCourseLogDO rangeCourseLogDO = new RangeCourseLogDO();
                rangeCourseLogDO.setTaskId(taskId);
                rangeCourseLogDO.setClassesId(classesId);
                rangeCourseLogDO.setOrderId(weeklyCourseOrderIdEnum.getOrderId());
                rangeCourseLogDO.setName(weeklyCourseOrderIdEnum.getDesc());
                rangeCourseLogDOList.add(rangeCourseLogDO);
            }
        }
        saveBatch(rangeCourseLogDOList);
    }

    public Map<Long, List<RangeCourseLogDO>> getMapByTaskId(Long taskId) {
        Map<Long, List<RangeCourseLogDO>> map = new HashMap<>();
        for (RangeCourseLogDO t : getListByTaskId(taskId)) {
            if (t != null) {
                if (Objects.nonNull(t.getClassesId())) {
                    map.computeIfAbsent(t.getClassesId(), k -> new ArrayList<>()).add(t);
                }
            }
        }
        return map;
    }


    public List<RangeCourseLogDO> getListByTaskId(Long taskId) {
        LambdaQueryWrapper<RangeCourseLogDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RangeCourseLogDO::getTaskId, taskId);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<RangeCourseLogDO> getListByTaskIdAndClassesId(Long taskId, Long classesId) {
        LambdaQueryWrapper<RangeCourseLogDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RangeCourseLogDO::getTaskId, taskId);
        queryWrapper.eq(RangeCourseLogDO::getClassesId, classesId);
        return getBaseMapper().selectList(queryWrapper);
    }

    public RangeCourseLogDO getOneLog(Long taskId, Long classesId, Integer orderId) {
        LambdaQueryWrapper<RangeCourseLogDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RangeCourseLogDO::getTaskId, taskId);
        queryWrapper.eq(RangeCourseLogDO::getClassesId, classesId);
        queryWrapper.eq(RangeCourseLogDO::getOrderId, orderId);
        return getBaseMapper().selectOne(queryWrapper);
    }
}
