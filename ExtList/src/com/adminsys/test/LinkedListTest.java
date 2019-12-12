package com.adminsys.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-12 10-23
 **/

/**
 *  1. 在底层使用静态内部类Node节点存放节点元素
 */
public class LinkedListTest {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("-1");
        linkedList.add("-2");
        System.out.println(linkedList.get(1));
    }
}
