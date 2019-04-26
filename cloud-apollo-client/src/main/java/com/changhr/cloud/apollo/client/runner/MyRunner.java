package com.changhr.cloud.apollo.client.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author changhr
 */
@Slf4j
@Component
public class MyRunner implements ApplicationRunner {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private CountDownLatch latch;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("sending message...");
        template.convertAndSend("chat", "Hello from Redis!");
        latch.await();
    }
}
