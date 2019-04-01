package com.mmall.concurrency.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * corePoolSize 核心线程数量，当新任务提交时，如果池中线程小于该数量，即使其他线程是空闲也直接创建新线程
 * maximumPoolSize 线程最大线程数量，当新任务提交时，如果当前线程数量大于 corePoolSize 并且小于maximumPoolSize，那么只有当
 * workQueue队列满才创建新的线程。如果此时workQueue也满了，那么通过拒绝策略来决定如何处理
 * workQueue 阻塞队列，存储等待执行的任务
 */
public class ThreadPoolDemo {

    static Logger log = LoggerFactory.getLogger(ThreadPoolDemo.class);
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
