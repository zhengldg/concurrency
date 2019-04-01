package com.mmall.concurrency.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 控制某个资源能被同时访问的个数，例如控制数据库连接最多只能连接20
 */
public class SemaporeDemo {

    static Logger log = LoggerFactory.getLogger(SemaporeDemo.class);
    static final int threadCount = 20;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < threadCount; i++) {
            final int t = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(t);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        log.info("finished");
        executorService.shutdown();
    }

    static void update(int i) throws InterruptedException {
        System.out.println(i);
        Thread.sleep(1000);
    }
}
