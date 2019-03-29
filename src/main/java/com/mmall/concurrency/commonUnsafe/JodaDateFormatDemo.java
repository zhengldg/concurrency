package com.mmall.concurrency.commonUnsafe;


import com.mmall.concurrency.annoations.NotThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * SimpleDateFormat是线程不安全的，可以通过使用局部变量方式解决
 */
@ThreadSafe
public class JodaDateFormatDemo {

    static Logger log = LoggerFactory.getLogger(JodaDateFormatDemo.class);
    public static int totalRequest = 10000; //总共请求数量
    public static int maxThread = 500; //请求用户数量

    /**
     * 使用静态变量多线程存在线程不安全问题
     */
    static DateTimeFormatter simpleDateFormat = DateTimeFormat.forPattern("yyyyMMdd");

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
    }

    //synchronized
    private static void update() {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info(simpleDateFormat.parseDateTime("20180101").toString("yyyy-MM-dd"));

    }
}
