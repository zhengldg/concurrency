package com.mmall.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SimulationConcurrencyCode {
    static Logger log = LoggerFactory.getLogger(SimulationConcurrencyCode.class);

    public static int totalRequest = 5000; //总共请求数量
    public static int maxThread = 500; //请求用户数量
    public static int count = 0;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(maxThread);
        log.info(String.valueOf(semaphore.availablePermits()));
        final CountDownLatch countDownLatch = new CountDownLatch(totalRequest);
        log.info("begin..");
        for (int i = 0; i < totalRequest; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("result : {}", count);
    }

    private static void add() {
        count++;
    }
}
