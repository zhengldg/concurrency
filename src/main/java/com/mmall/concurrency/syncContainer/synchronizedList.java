package com.mmall.concurrency.syncContainer;


import com.google.common.collect.Lists;
import com.mmall.concurrency.annoations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Vector是线程安全的类，其方法添加了 synchronized关键字
 */
@ThreadSafe
public class synchronizedList {

    static Logger log = LoggerFactory.getLogger(synchronizedList.class);
    public static int totalRequest = 10000; //总共请求数量
    public static int maxThread = 500; //请求用户数量
    static List<Integer> list = Collections.synchronizedList(Lists.newArrayList());

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
        System.out.println(list.size());
    }

    //synchronized
    private static void update() {
        list.add(1);
    }
}
