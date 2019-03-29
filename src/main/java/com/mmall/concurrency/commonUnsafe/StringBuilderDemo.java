package com.mmall.concurrency.commonUnsafe;


import com.mmall.concurrency.annoations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * StringBuilder是线程不安全的，可以换成StringBuffer类 或者添加synchronized
 */
@NotThreadSafe
public class StringBuilderDemo {

    static Logger log = LoggerFactory.getLogger(StringBuilderDemo.class);
    public static int totalRequest = 1000000; //总共请求数量
    public static int maxThread = 500; //请求用户数量
    static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(maxThread);
        final CountDownLatch countDownLatch = new CountDownLatch(totalRequest);
        long start = System.currentTimeMillis();
        for (int i = 0; i < totalRequest; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("cost : {}", System.currentTimeMillis() - start);
        System.out.println(stringBuilder.length());
    }

    //synchronized
    private static void update() {
        stringBuilder.append(1);
    }
}
