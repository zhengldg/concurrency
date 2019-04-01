package com.mmall.concurrency.syncContainer;


import com.mmall.concurrency.annoations.NotThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

/**
 * 多个线程同时对vector集合增、删元素，可能会导致异常发生
 */
@NotThreadSafe
public class VectorDemo2 {

    static Logger log = LoggerFactory.getLogger(VectorDemo2.class);
    public static int totalRequest = 10000; //总共请求数量
    public static int maxThread = 500; //请求用户数量
    static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread thread = new Thread() {
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            };

            Thread thread1 = new Thread() {
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.get(i);
                    }
                }
            };
            thread.start();
            thread1.start();
        }
    }
}
