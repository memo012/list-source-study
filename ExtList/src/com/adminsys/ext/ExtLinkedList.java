package com.adminsys.ext;

import java.util.NoSuchElementException;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-12 12-23
 **/
public class ExtLinkedList<E> implements ExtList<E> {

    /**
     * 集合大小
     */
    transient int size = 0;

    /**
     * 最后一个Node对象
     */
    transient Node<E> last;

    /**
     * 第一个Node对象
     */
    transient Node<E> first;

    /**
     * Node节点
     *
     * @param <E>
     */
    private static class Node<E> {
        /**
         * 节点元素值
         */
        E item;
        /**
         * 当前节点指向当前节点下一个节点
         */
        Node<E> next;
        /**
         * 当前节点指向当前节点上一个节点
         */
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }


    @Override
    public int size() {
        return size;
    }

    /**
     * 获取首个Node对象
     *
     * @return
     */
    public E getFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }

    /**
     * 获取最后一个Node对象
     *
     * @return
     */
    public E getLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    /**
     * 检查是否越界 index是否符合
     *
     * @param index
     */
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    Node<E> node(int index) {
        /* 折半查找思想
            首先先把Node对象长度均等分为两个区域，然后先比较要查询的下标处于那块区域，
            然后进行搜索查询
         */
        if (index < (size >> 1)) {
            // 获取到第一个节点
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            // 获取到最后一个节点
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            // 从开始到结尾进行扫描，如果查到成功，即调用unlink()方法
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    E unlink(Node<E> x) {
        // 获取删除节点对象的元素
        final E element = x.item;
        // 获取删除节点对象的下一个节点对象
        final Node<E> next = x.next;
        // 获取删除节点对象的上一个节点对象
        final Node<E> prev = x.prev;
        // 删除的节点对象为首节点
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
//        modCount++;
        return element;
    }

    void linkLast(E e) {
        // 获取添加节点前的最后一个节点
        final Node<E> l = last;
        // 把当前元素封装成Node对象
        final Node<E> newNode = new Node<>(l, e, null);
        // 把当前对象赋给last对象
        last = newNode;
        if (l == null) {
            // 第一次添加Node对象
            first = newNode;
        } else {
            // 把上一个Node对象中next属性指向当前Node对象
            l.next = newNode;
        }
        size++;
//        modCount++;  线程安全
    }


}
