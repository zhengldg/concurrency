package com.mmall.concurrency.concurrent;


import com.mmall.concurrency.annoations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * ConcurrentHashMap
 */
@ThreadSafe
public class concurrentHashMapDemo {

    static Logger log = LoggerFactory.getLogger(concurrentHashMapDemo.class);
    public static int totalRequest = 10000; //总共请求数量
    public static int maxThread = 500; //请求用户数量
    static ConcurrentHashMap list = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(maxThread);
        final CountDownLatch countDownLatch = new CountDownLatch(totalRequest);
        long start = System.currentTimeMillis();
        for (int i = 0; i < totalRequest; i++) {
            final int t = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(t);
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("cost : {}", System.currentTimeMillis() - start);
        System.out.println(list.size());
    }

    //synchronized
    private static void update(int i) {
        list.put(i,i);
    }
}
