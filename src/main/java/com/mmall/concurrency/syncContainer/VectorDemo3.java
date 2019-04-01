package com.mmall.concurrency.syncContainer;

import java.util.Iterator;
import java.util.Vector;

/**
 * 使用 iterator\foreach方式迭代 vector\List等 容器时同时删除元素会造成异常，可以先标记元素后后面统一删除
 *
 */
public class VectorDemo3 {
    /**
     * java.util.ConcurrentModificationException
     * @param vector
     */
    static void test1(Vector<Integer> vector){
        for(Integer i : vector){
            if(i == 3){
                vector.remove(i);
            }
        }
    }

    /**
     * java.util.ConcurrentModificationException
     * @param vector
     */
    static void test2(Vector<Integer> vector){
        Iterator<Integer> iterator = vector.iterator();
        while (iterator.hasNext()){
            Integer val = iterator.next();
            if(val == 3){
                vector.remove(val);
            }
        }
    }

    /**
     * success
     * @param vector
     */
    static void test3(Vector<Integer> vector){
        for(Integer i=0; i<vector.size(); i++){
            if(vector.get(i) == 3){
                vector.remove(vector.get(i));
            }
        }
    }

    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();
        vector.add(3);
        vector.add(2);
        vector.add(1);
        test3(vector);
        System.out.println(vector.toString());
    }
}
