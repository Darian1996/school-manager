package com.darian.schoolmanager.common.utils.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/12/3  22:52
 */
public class ServiceLogInterceptor extends BaseLogInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(ServiceLogInterceptor.class);

    @Override
    protected Logger getPrivateLOGGER() {
        return LOGGER;
    }
}
