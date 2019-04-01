package com.mmall.concurrency.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 控制一组数量的线程都准备就绪后，这些线程再继续往下执行
 */
public class CyclicBarrierDemo {

    static Logger log = LoggerFactory.getLogger(CyclicBarrierDemo.class);
    static final int threadCount = 20;
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < threadCount; i++) {
            final int t = i;
            Thread.sleep(1000);
            executorService.execute(() -> {
                update(t);
            });
        }
        log.info("finished");
        executorService.shutdown();
    }

    static void update(int i) {
        log.info("thread:{} is awaiting ", i);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        log.info("thread:{} is continue ", i);
    }
}
