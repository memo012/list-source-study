package com.adminsys.ext;

/**
 * @author qiang
 * @description
 * @create 2019-12-14 13-00
 **/
public class Ext7HashMap<K, V> implements ExtMap<K, V> {

    /**
     *  初始容量16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     *  加载因子 作用 对我们的数组进行扩容
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     *  允许最大的容量
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     *  设置实际的加载因子
     */
    final float loadFactor;

    static final Node<?,?>[] EMPTY_TABLE = {};

    /**
     *  hashMap底层数组为空
     */
    transient Node<K,V>[] table = (Node<K,V>[]) EMPTY_TABLE;

    /**
     * 阈值 需要扩容的时候设置值  实际hashMap存放的大小
     *
     * 容量 * 加载因子(0.75)
     *
     * 当达到阈值情况下扩容
     */
    int threshold;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V put(K key, V value) {
        // 如果table为空，初始化数组
        if (table == EMPTY_TABLE) {
            inflateTable(threshold);
        }
        // 计算hash值
        int hash = hash(key);
        // 根据hash值计算  数组下标存放的位置
        int index = indexFor(hash, table.length);
        // 判断hashCode相同 且 对象值相同的情况下，修改value值
        for (Node<K,V> e = table[index]; e != null; e = e.next) {
            Object k;
            // 基本类型和引用类型判断
            if (e.hash == hash && ((k = e.getKey()) == key || key.equals(k))) {
                V oldValue = e.getValue();
                e.setValue(value);
//                e.recordAccess(this);
                return oldValue;
            }
        }
        return null;
    }

    /**
     *  计算数组下标
     * @param h
     * @param length
     * @return
     */
    static int indexFor(int h, int length) {
        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
        return h & (length-1);
    }

    /**
     *  计算hash值
     * @param k
     * @return
     */
    final int hash(Object k) {
        int h = 0;
        if (0 != h && k instanceof String) {
            return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     *  默认数组初始化
     * @param toSize
     */
    private void inflateTable(int toSize) {
        // Find a power of 2 >= toSize
        int capacity = roundUpToPowerOf2(toSize);
        // 计算初始化容量
        threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
        // 数组中的容量初始化
        table = new Node[capacity];
//        initHashSeedAsNeeded(capacity);
    }

    private static int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        return number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }


    /**
     *  默认的hashMap初始值16  加载因子为0.75
     */
    public Ext7HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     *
     * @param initialCapacity 初始的容量
     * @param loadFactor 加载因子
     */
    public Ext7HashMap(int initialCapacity, float loadFactor) {
        // 检查设置的初始化容量是否合理
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        // 最大容量限制
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        // 检查加载因子是否合理
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        // 设置我们的加载因子和实际hashMap存放的阈值
        this.loadFactor = loadFactor;
        threshold = initialCapacity;
        init();
    }

    /**
     *  定义的空方法能够给子类实现扩展功能
     */
    void init() {
    }

    @Override
    public V get(K key) {
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
         *  下一个Node对象
         */
        Node<K,V> next;

        int hash;

        /**
         *  使用构造函数赋值
         * @param k
         * @param v
         */
        public Node(int h, K k, V v,  Node<K,V> n) {
            this.k = k;
            this.v = v;
            this.next = n;
            hash = h;
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
