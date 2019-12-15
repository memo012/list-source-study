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
     *  集合大小
     */
    transient int size;

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
        return size;
    }

    @Override
    public V put(K key, V value) {
        // 如果table为空，初始化数组
        if (table == EMPTY_TABLE) {
            inflateTable(threshold);
        }
        if (key == null)
            return putForNullKey(value);
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
        addEntry(hash, key, value, index);
        return null;
    }

    private V putForNullKey(V value) {
        for (Node<K,V> e = table[0]; e != null; e = e.next) {
            if (e.k == null) {
                V oldValue = e.v;
                e.v = value;
//                e.recordAccess(this);
                return oldValue;
            }
        }
//        modCount++;
        addEntry(0, null, value, 0);
        return null;
    }


    /**
     * @param hash
     * @param key
     * @param value
     * @param bucketIndex 桶索引下标
     */
    void addEntry(int hash, K key, V value, int bucketIndex) {
        if ((size >= threshold) && (null != table[bucketIndex])) {
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }

    void createEntry(int hash, K key, V value, int bucketIndex) {
        // 获取原来的 Node对象 如果获取为空的情况下  没有发生hash冲突
        Node<K,V> next = table[bucketIndex];
        // 当前最新的Node对象
        table[bucketIndex] = new Node<>(hash, key, value, next);
    }

//    public V get(Object key) {
//        if (key == null)
//            return getForNullKey();
//        Entry<K,V> entry = getEntry(key);
//
//        return null == entry ? null : entry.getValue();
//    }

    /**
     *  扩容
     * @param newCapacity
     */
    void resize(int newCapacity) {
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        /**
         *  最大限度扩容
         */
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        Node[] newTable = new Node[newCapacity];
        transfer(newTable, initHashSeedAsNeeded(newCapacity));
//        table = newTable;
        threshold = (int)Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
    }

    void transfer(Node[] newTable, boolean rehash) {
        // 新数组长度
        int newCapacity = newTable.length;
        for (Node<K,V> e : table) {
            while(null != e) {
                Node<K,V> next = e.next;
                if (rehash) {
                    e.hash = null == e.k ? 0 : hash(e.k);
                }
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }

    final boolean initHashSeedAsNeeded(int capacity) {
        return true;
    }

    /**
     *  计算数组下标
     * @param h
     * @param length
     * @return
     */
    static int indexFor(int h, int length) {
        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
        // 减少下标重复
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
        if (key == null)
            return getForNullKey();
        Node<K,V> entry = getEntry(key);

        return null == entry ? null : entry.getValue();
    }

    final Node<K,V> getEntry(Object key) {
        /**
         *  集合大小为空
         */
        if (size == 0) {
            return null;
        }

        // 计算hash值
        int hash = (key == null) ? 0 : hash(key);
        // 遍历取值
        for (Node<K,V> e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.k) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    private V getForNullKey() {
        if (size == 0) {
            return null;
        }
        for (Node<K,V> e = table[0]; e != null; e = e.next) {
            if (e.k == null)
                return e.v;
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
            this.hash = h;
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
