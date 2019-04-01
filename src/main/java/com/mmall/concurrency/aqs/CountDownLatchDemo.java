package com.mmall.concurrency.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch 保证所有线程都执行完再执行接下来的代码
 */
public class CountDownLatchDemo {

    static Logger log = LoggerFactory.getLogger(CountDownLatchDemo.class);
    static final int threadCount = 200;
    static CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int t = i;
            executorService.execute(() -> {
                try {
                    update(t);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        // 这里可以加等待时间控制
        countDownLatch.await();
        log.info("finished");
        executorService.shutdown();
    }

    static void update(int i) {
        log.info(String.valueOf(i));
    }
}
