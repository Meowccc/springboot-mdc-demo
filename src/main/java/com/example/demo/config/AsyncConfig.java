package com.example.demo.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/**
 * @author meow
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean("asyncExecutor")
    public Executor asyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("async-");
        return executor;
    }

    @Bean("mdcAsyncExecutor")
    public Executor mdcAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("mdc-async-");
        executor.setTaskDecorator(this::withMdc);
        return executor;
    }

    public Runnable withMdc(final Runnable runnable) {
        final Map<String, String> contextMap = MDC.getCopyOfContextMap();
        final RequestAttributes context = RequestContextHolder.currentRequestAttributes();
        return () -> {
            try {
                Optional.ofNullable(contextMap)
                        .filter(Predicate.not(Map::isEmpty))
                        .ifPresent(MDC::setContextMap);
                RequestContextHolder.setRequestAttributes(context);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
