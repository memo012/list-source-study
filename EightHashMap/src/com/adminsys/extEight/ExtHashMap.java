package com.adminsys.extEight;

import com.adminsys.ext.ExtMap;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-17 07-57
 **/
public class ExtHashMap<K, V> implements ExtMap<K, V> {

    /**
     * 阈值
     */
    final float loadFactor;

    /**
     * 集合大小
     */
    transient int size;

    /**
     * 默认容量大小
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 加载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * hash冲突的数组
     */
    transient Node<K, V>[] table;

    /**
     * 阈值 = 加载因子*数组容量大小
     */
    int threshold;

    /**
     * 限制数组扩容的最大值
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 分界值(用于是否采用红黑树存值)
     */
    static final int TREEIFY_THRESHOLD = 8;

    @Override
    public int size() {
        return 0;
    }

    public ExtHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    /**
     * 计算hash值
     *
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K, V>[] tab;
        // 单个链表中的某个节点,就是我们根据key计算的index  存放的来 链表
        Node<K, V> p;
        // n 表示我们当前的数组的长度   i 为当前key计算hash值存放的数组的index位置
        int n, i;
        // 如果当前我们的数组为空时 进行扩容
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        // 如果从数组中没有获取到对应的链表 就是开始赋值
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K, V> e;
            // key值
            K k;
            // 如果hash值相同并且key相同的情况下 实现value值覆盖
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else {
                // binCount 继续循环
                for (int binCount = 0; ; ++binCount) {
                    // 没有下一个节点
                    if ((e = p.next) == null) {
                        // hash值相同  可能值不同
                        // p 表示index值不同的数组中链表的最后一个节点
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    // 如果hash值相同且可以key值相同 实现覆盖
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //e != null表示存在与插入值相同的key，直接进行覆盖
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
//                afterNodeAccess(e);
                return oldValue;
            }
        }
        //当数组大小超过容量阈值时，进行扩容
        if (++size > threshold)
            resize();
        return null;
    }

    final void treeifyBin(Node<K, V>[] tab, int hash) {

    }


    /**
     * 新增节点
     *
     * @param hash
     * @param key
     * @param value
     * @param next
     * @return
     */
    private Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }

    private Node<K, V>[] resize() {
        // 获取原来的数组长度
        Node<K, V>[] oldTab = table;
        // 获取原来数组的容量
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // 原来的阈值大小
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            // 原来的容量大小大于最大扩容的大小
            if (oldCap >= MAXIMUM_CAPACITY) {
                // 加载因子的阈值为Integer最大值
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            // 新的容量大小为原来的两倍   并且原来的容量大于默认16容量
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                // 新的加载因子容量为原来加载因子容量的两倍
                newThr = oldThr << 1; // double threshold
        } else if (oldThr > 0)
            newCap = oldThr;
        else {
            // 新的容量为默认16
            newCap = DEFAULT_INITIAL_CAPACITY;
            // 阈值=默认容量*加载因子
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        // 如果新的阈值为0的情况下
        if (newThr == 0) {
            // 新的容量*0.75
            float ft = (float) newCap * loadFactor;
            // 新的加载因子阈值
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        // 加载因子的阈值 为新的
        threshold = newThr;
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;
        return newTab;
    }

    static class Node<K, V> {
        /**
         * hash值
         */
        final int hash;
        /**
         * key值
         */
        final K key;
        /**
         * 当前节点的元素数据
         */
        V value;
        /**
         * 下一个节点
         */
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

    }

    @Override
    public V get(K key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            // 判断定位到数组下标第一个节点是否为所要的节点
            if (first.hash == hash &&
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            // 判断是否存在下一个节点
            if ((e = first.next) != null) {
                //如果第一个节点是红黑树的节点，则进行红黑树节点查找
//                if (first instanceof TreeNode)
//                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                // 去链表中查找
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }


}
