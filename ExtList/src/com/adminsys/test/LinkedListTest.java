package com.adminsys.test;

import com.adminsys.ext.ExtLinkedList;

import java.util.LinkedList;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-12 10-23
 **/

/**
 *  链表数据结构底层原理实现 双向列表头尾相接
 *  1. 在底层使用静态内部类Node节点存放节点元素
 *      三个属性 pred(关联的上一个节点) item 当前的值  next  下一个节点
 *  2. Add原理是如何实现的  一直在链表之后新增
 *  3. Get原理  链表缺点： 查询效率非常低  所以linkedList 中采用折半查找  范围查找定位node节点
 *  4. 删除原理 就是改变头尾相结合 指向
 */
public class LinkedListTest {
    public static void main(String[] args) {
        ExtLinkedList<String> linkedList = new ExtLinkedList<>();
        linkedList.add("-1");
        linkedList.add("-2");
        System.out.println(linkedList.get(1));
    }
}
