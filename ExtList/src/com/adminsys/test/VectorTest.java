package com.adminsys.test;

import java.util.ArrayList;
import java.util.Vector;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-12 10-23
 **/

public class VectorTest {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        new ArrayList<>();
        /**
         *  Vector 与ArrayList 集合区别
         *  相同点：
         *      底层都是采用数组实现
         *  不同的：
         *      1. 默认初始化时候
         *          a. ArrayList默认不会初始化 (第一次调用add()方法时才会初始化)
         *          b. Vector 默认 初始化大小为0
         *      2. 数组扩容
         *          a. ArrayList集合底层数组扩容默认为原来的50%
         *          b. Vector集合底层数组扩容默认为原来的100%
         *      3. 线程安全
         *          a. ArrayList 线程不安全
         *          b. Vector 线程安全
         */
    }
}
