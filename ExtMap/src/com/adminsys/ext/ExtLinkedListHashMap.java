package com.adminsys.ext;

import java.util.LinkedList;

/**
 * @Author: qiang
 * @Description: 基于LinkedList集合实现HashMap
 * @Create: 2019-12-13 05-35
 **/
public class ExtLinkedListHashMap<K,V> implements ExtMap<K,V> {

    /**
     *
     */
    private LinkedList<Node>[] objects = new LinkedList[100];

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V put(K key, V value) {
        // 计算出我们数组存放的下标
        int hash = hash(key);
        // 查询相同的hashCode linkedList 集合是否存在
        LinkedList<Node> linkedList = objects[hash];
        Node node = new Node(key, value);
        if(linkedList == null){
            linkedList = new LinkedList();
            linkedList.add(node);
            objects[hash] = linkedList;
            return value;
        }
        // 说明hashCode产生了冲突问题 使用equals判断值是否相同 判断是否修改对应的value
        for (Node e:
                linkedList) {
            // hashCode 相同 值也相同
            if(key.equals(e.getKey())){
                e.setValue(value);
                return value;
            }
        }
        // hashCode相同  值不相同
        linkedList.add(node);
        objects[hash] = linkedList;
        return value;
    }

    private int hash(K key){
        int hashCode = key.hashCode();
        // hashCode与equals 获取数组中存放的下标位置
        int hash = hashCode % objects.length;
        return hash;
    }

    @Override
    public V get(K key) {
        if(key == null){
            return null;
        }
        int hash = hash(key);
        LinkedList<Node> linkedList = objects[hash];
        for (Node e:
                linkedList) {
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
