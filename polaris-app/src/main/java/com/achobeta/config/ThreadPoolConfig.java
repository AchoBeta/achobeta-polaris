package com.achobeta.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class ThreadPoolConfig {

    @Bean
    @ConditionalOnMissingBean(ThreadPoolExecutor.class)
    public ThreadPoolTaskExecutor threadPoolExecutor(ThreadPoolConfigProperties properties) {
        // 实例化策略
        RejectedExecutionHandler handler;
        switch (properties.getPolicy()){
            case "AbortPolicy":
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case "DiscardPolicy":
                handler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            case "DiscardOldestPolicy":
                handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case "CallerRunsPolicy":
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            default:
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
        }
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() {
            /**
             * 所有线程都会委托给这个execute方法，在这个方法中我们把父线程的MDC内容赋值给子线程
             */
            @Override
            public void execute(Runnable runnable) {
                // 获取父线程MDC中的内容，必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
                Map<String, String> context = MDC.getCopyOfContextMap();
                super.execute(() -> {
                    // 将父线程的MDC内容传给子线程
                    if (context != null) {
                        MDC.setContextMap(context);
                    }
                    try {
                        // 执行异步操作
                        runnable.run();
                    } finally {
                        // 清空MDC内容
                        MDC.clear();
                    }
                });
            }
            @Override
            public <T> Future<T> submit(Callable<T> task) {
                // 获取父线程MDC中的内容，必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
                Map<String, String> context = MDC.getCopyOfContextMap();
                return super.submit(() -> {
                    // 将父线程的MDC内容传给子线程
                    if (context != null) {
                        MDC.setContextMap(context);
                    }
                    try {
                        // 执行异步操作
                        return task.call();
                    } finally {
                        // 清空MDC内容
                        MDC.clear();
                    }
                });
            }
        };
        executor.setCorePoolSize(properties.getCorePoolSize());
        // 配置最大线程数
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        // 空线程回收时间15s
        executor.setKeepAliveSeconds(properties.getKeepAliveTime());
        Executors.defaultThreadFactory();
        // 配置队列大小
        executor.setQueueCapacity(properties.getBlockQueueSize());
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("ab-polaris-service-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(handler);
        // 执行初始化
        executor.initialize();
        return executor;
    }

}
