package com.rokid.skill.kit4j.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author wuyukai on 2018/7/20.
 */
@EnableAsync
@Configuration
public class AsyncTaskConfig {

        @Bean("logTaskExecutor")
        public Executor taskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(4);
            executor.setMaxPoolSize(10);
            executor.setQueueCapacity(100);
            executor.setKeepAliveSeconds(60);
            executor.setThreadNamePrefix("logTaskExecutor-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            return executor;
        }

}
