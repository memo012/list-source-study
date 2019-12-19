package com.adminsys.ext;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-13 04-54
 **/
public interface ExtMap<K, V> {
    /**
     *  集合大小
     * @return
     */
    int size();

    /**
     *  添加
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);

    /**
     *  查询
     * @param key
     * @return
     */
    V get(K key);

    interface Entry<K,V> {
        /**
         *  获取key
         * @return
         */
        K getKey();

        /**
         *  获取value
         * @return
         */
        V getValue();

        /**
         *
         * @return
         */
        V setValue(V value);
    }

}
