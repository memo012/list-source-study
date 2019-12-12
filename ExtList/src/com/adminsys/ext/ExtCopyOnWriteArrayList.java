package com.adminsys.ext;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class ExtCopyOnWriteArrayList<E> implements ExtList<E> {

    /**
     * 存放我们的数据的数组
     * transient 非序列化
     * volatile 可见性
     */
    private transient volatile Object[] array;

    /**
     *  加锁 高并发保证数据的正确性
     */
    final transient ReentrantLock lock = new ReentrantLock();

    @Override
    public int size() {
        return getArray().length;
    }

    @Override
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            // 获取原来数组
            Object[] elements = getArray();
            // 数组长度
            int len = elements.length;
            // 把原来数据中的数据复制到长度为len+1的新的数组中
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            // 赋值
            newElements[len] = e;
            // 返回新的数组对象
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }

    final Object[] getArray() {
        return array;
    }

    @Override
    public E get(int index) {
        return get(getArray(), index);
    }

    private E get(Object[] a, int index) {
        return (E) a[index];
    }

    /**
     *  构造函数初始化数组 默认数组大小为0
     */
    public ExtCopyOnWriteArrayList() {
        setArray(new Object[0]);
    }

    final void setArray(Object[] a) {
        array = a;
    }


}
