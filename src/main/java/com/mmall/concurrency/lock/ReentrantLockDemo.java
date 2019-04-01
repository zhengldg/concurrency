package com.mmall.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock独有的功能
 * 可指定是公平锁还是非公平锁
 * 提供了一个Condition类，可以分组唤醒需要唤醒的线程
 * 提供能够中断等待锁的线程的机制，lock.lockInterruptibly()
 */
public class ReentrantLockDemo {

    static Logger log = LoggerFactory.getLogger(ReentrantLockDemo.class);
    static final int threadCount = 100;
    static int total = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int t = i;
            executorService.execute(() -> {
                log.info(String.valueOf(t));
            });
        }
        log.info("finished");
        log.info(String.valueOf(total));
        executorService.shutdown();
    }

}
