package com.darian.schoolmanager.rangecourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.utils.AssertUtils;
import com.darian.schoolmanager.rangecourse.DO.RangeCourseTaskDO;
import com.darian.schoolmanager.rangecourse.DTO.RangeCourseTaskEnum;
import com.darian.schoolmanager.rangecourse.DTO.RangeCourseTaskEnum.RangeCourseTaskStatusEnums;
import com.darian.schoolmanager.rangecourse.mapper.RangeCourseTaskMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/12  1:14
 */
@Service
public class RangeCourseTaskService
        extends ServiceImpl<RangeCourseTaskMapper, RangeCourseTaskDO>
        implements IService<RangeCourseTaskDO> {

    public RangeCourseTaskDO initRangeCourseTask() {
        RangeCourseTaskDO rangeCourseTaskDO = new RangeCourseTaskDO();
        rangeCourseTaskDO.setStatus(RangeCourseTaskEnum.RangeCourseTaskStatusEnums.INIT.getCode());
        save(rangeCourseTaskDO);
        return rangeCourseTaskDO;
    }

    public void completeTask(Long id) {
        RangeCourseTaskDO rangeCourseTaskDO = getById(id);
        AssertUtils.assertTrue(Objects.nonNull(rangeCourseTaskDO), "任务找不到");
        rangeCourseTaskDO.setStatus(RangeCourseTaskEnum.RangeCourseTaskStatusEnums.SUCCESS.getCode());
        updateById(rangeCourseTaskDO);
    }

    public void failTask(Long id) {
        RangeCourseTaskDO rangeCourseTaskDO = getById(id);
        AssertUtils.assertTrue(Objects.nonNull(rangeCourseTaskDO), "任务找不到");
        rangeCourseTaskDO.setStatus(RangeCourseTaskStatusEnums.FAIL.getCode());
        updateById(rangeCourseTaskDO);
    }
}
