package com.noah.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Slf4j
public class HelloService {

    @Async
    public void asyncHello() {
        try {
            TimeUnit.SECONDS.sleep(1);
            log.info("thread name:{}", Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public Integer asyncReturn() {
        return 1;
    }

    @Async
    public Future<Integer> asyncReturnV2() {
        return new AsyncResult<>(2);
    }

    @Async
    public Future<Integer> asyncReturnV3() {
        return CompletableFuture.completedFuture(3);
    }

    static RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

    static Executor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10), handler);

    public static void main(String[] args) {

        try {
            for (int i = 0; i < 1000; i++) {

                executor.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("" + Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rejectThreadPool() {

    }
}