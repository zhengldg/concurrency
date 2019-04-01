package com.mmall.concurrency.deadLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 死锁四个条件：
 * 1.互斥条件；
 * 2.请求和保持条件；
 * 3.不可剥夺条件；
 * 4.环路等待条件；
 *
 * 解决死锁的方法：
 * 1.注意加锁的顺序；
 * 2.添加加锁限时，超过时间释放占用资源
 * 3.死锁检测
 */
public class DeadLockDemo implements Runnable {
    static Logger log = LoggerFactory.getLogger(DeadLockDemo.class);
    private int flag;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println("flag:" + flag);
        if (flag == 1) {
            synchronized (obj1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2) {

                }
            }
            log.info(" flag1 over.");
        } else {
            synchronized (obj2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1) {

                }
            }
            log.info(" flag2 over.");
        }
    }

    static Object obj1 = new Object(), obj2 = new Object();

    public static void main(String[] args) {
        DeadLockDemo t1 = new DeadLockDemo();
        DeadLockDemo t2 = new DeadLockDemo();
        t1.setFlag(1);
        t2.setFlag(2);
        new Thread(t1).start();
        new Thread(t2).start();
    }
}
