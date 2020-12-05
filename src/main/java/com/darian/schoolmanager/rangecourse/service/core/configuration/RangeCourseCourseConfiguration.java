package com.darian.schoolmanager.rangecourse.service.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class RangeCourseCourseConfiguration {

    @Bean(name = "rangeCourseProcessorThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor rangeCourseProcessorThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(10);
        // 设置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(10);
        // 设置队列容量
        threadPoolTaskExecutor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        threadPoolTaskExecutor.setThreadNamePrefix("RangeCourseProcessor-");
        // 设置拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskExecutor;
    }
}