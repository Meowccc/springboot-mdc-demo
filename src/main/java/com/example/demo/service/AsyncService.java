package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author meow
 */
@Slf4j
@Service
public class AsyncService {

    @Async("asyncExecutor")
    public void asyncMethod() {
        log.info("asyncMethod");
    }
    @Async("mdcAsyncExecutor")
    public void mdcAsyncMethod() {
        log.info("mdcAsyncMethod");
    }
}
