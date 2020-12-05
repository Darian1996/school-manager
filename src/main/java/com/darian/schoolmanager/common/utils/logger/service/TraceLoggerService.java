package com.darian.schoolmanager.common.utils.logger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darian.schoolmanager.common.utils.logger.DO.TraceLoggerModuleDO;
import com.darian.schoolmanager.common.utils.logger.mapper.TraceLoggerMapper;
import org.springframework.stereotype.Service;


/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2020/12/3  22:43
 */
@Service
public class TraceLoggerService extends
        ServiceImpl<TraceLoggerMapper, TraceLoggerModuleDO>
        implements IService<TraceLoggerModuleDO> {

}
