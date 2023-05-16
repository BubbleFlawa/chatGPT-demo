package com.asleepyfish.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean("normalThreadPool")  //线程池实例名，多个线程池配置需要声明，一个线程池可有可无
    public ThreadPoolTaskExecutor executorNormal() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(3);
        //最大线程数
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(3);
        executor.setKeepAliveSeconds(60);
        //线程名称
        executor.setThreadNamePrefix("NORMAL--");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}