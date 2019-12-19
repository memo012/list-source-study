package com.adminsys.ext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qiang
 * @Description: 基于ArrayList集合实现HashMap
 * @Create: 2019-12-13 05-35
 **/
public class ExtArrayListHashMap<K,V> implements ExtMap<K,V> {

    /**
     *  存放我们的所有的键值对
     */
    private List<Node<K, V>> nodeList = new ArrayList<>();

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V put(K key, V value) {
        // 封装Node对象
        Node<K, V> node = new Node<>(key, value);
        // 往集合中添加
        nodeList.add(node);
        return value;
    }

    @Override
    public V get(K key) {
        // 直接遍历我们集合中的node对象，进行比较
        for (Entry e:
             nodeList) {
            if(key.equals(e.getKey())){
                return (V)e.getValue();
            }
        }
        return null;
    }

    /**
     *  Node 表示我们存放在hashMap中的一条键值对
     * @param <K>
     * @param <V>
     */
    static class Node<K,V> implements Entry<K,V>{
        /**
         *  key
         */
        private K k;

        /**
         *  value
         */
        private V v;

        /**
         *  使用构造函数赋值
         * @param k
         * @param v
         */
        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(V value) {
            this.v = value;
            return v;
        }
    }

}
