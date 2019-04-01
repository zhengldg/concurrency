package com.mmall.concurrency.deadLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过加锁限时解决死锁问题
 */
public class DeadLockAndUnlockDemo implements Runnable {
    static Logger log = LoggerFactory.getLogger(DeadLockAndUnlockDemo.class);
    static ReentrantLock reentrantLock1 = new ReentrantLock();
    static ReentrantLock reentrantLock2 = new ReentrantLock();
    private int flag;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println("flag:" + flag);
        if (flag == 1) {
            reentrantLock1.lock();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (!reentrantLock2.tryLock(1, TimeUnit.SECONDS)) {
                    log.info("reentrantLock2 get lock fail.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reentrantLock1.unlock();
            log.info(" flag1 over.");
        } else {
            reentrantLock2.lock();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (!reentrantLock1.tryLock(1, TimeUnit.SECONDS)) {
                    log.info("reentrantLock1 get lock fail.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reentrantLock2.unlock();
            log.info(" flag2 over.");
        }
    }

    static Object obj1 = new Object(), obj2 = new Object();

    public static void main(String[] args) {
        DeadLockAndUnlockDemo t1 = new DeadLockAndUnlockDemo();
        DeadLockAndUnlockDemo t2 = new DeadLockAndUnlockDemo();
        t1.setFlag(1);
        t2.setFlag(2);
        new Thread(t1).start();
        new Thread(t2).start();
    }
}
