package com.mmall.concurrency.commonUnsafe;


import com.mmall.concurrency.annoations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * HashMap\ArrayList\HashSet等常用集合类是线程不安全的
 */
@NotThreadSafe
public class HashMapDemo {

    static Logger log = LoggerFactory.getLogger(HashMapDemo.class);
    public static int totalRequest = 10000; //总共请求数量
    public static int maxThread = 500; //请求用户数量

    static HashMap hashMap = new HashMap();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(maxThread);
        final CountDownLatch countDownLatch = new CountDownLatch(totalRequest);
        long start = System.currentTimeMillis();
        for (int i = 0; i < totalRequest; i++) {
            final int temp = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(temp);
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("size: {}", hashMap.size());
        log.info("cost : {}", System.currentTimeMillis() - start);
    }

    //synchronized
    private  static void update(int i) {
        hashMap.put(i, i);
    }
}
