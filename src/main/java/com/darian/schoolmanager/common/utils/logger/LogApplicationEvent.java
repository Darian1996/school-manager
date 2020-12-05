package com.darian.schoolmanager.common.utils.logger;

import com.darian.schoolmanager.common.utils.logger.DO.TraceLoggerModuleDO;
import org.springframework.context.ApplicationEvent;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/12/4  0:04
 */
public class LogApplicationEvent extends ApplicationEvent {


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LogApplicationEvent(TraceLoggerModuleDO source) {
        super(source);
    }

    @Override
    public TraceLoggerModuleDO getSource() {
        return (TraceLoggerModuleDO) super.getSource();
    }
}
