package com.darian.schoolmanager;

import com.darian.schoolmanager.common.utils.logger.LogApplicationEvent;
import com.darian.schoolmanager.common.utils.logger.service.TraceLoggerService;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication()
@EnableTransactionManagement(proxyTargetClass = true)
public class SchoolManagerApplication implements ApplicationContextAware {

    private static TraceLoggerService traceLoggerService;


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SchoolManagerApplication.class);
        springApplication.addListeners(new ApplicationListener<LogApplicationEvent>() {
            @Override
            public void onApplicationEvent(LogApplicationEvent event) {
                traceLoggerService.save(event.getSource());
            }
        });
        springApplication.run(args);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        traceLoggerService = applicationContext.getBean(TraceLoggerService.class);
    }
}
