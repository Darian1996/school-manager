package com.darian.schoolmanager.common.utils.logger.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.darian.schoolmanager.common.modle.DataBaseModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("t_log_info")
public class TraceLoggerModuleDO extends DataBaseModel<TraceLoggerModuleDO> {

    private LocalDateTime loggerTime;

    private String traceId;

    private String classSimpleName;

    private String methodName;

    private String success;

    private Long costTimes;

    private String argsMsg;

    private String resultMsg;
}