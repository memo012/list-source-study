package com.adminsys.test;

import com.adminsys.extEight.ExtHashMap;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-19 08-01
 **/

public class HashMapTest {
    public static void main(String[] args) {
        ExtHashMap<Object, String> ex = new ExtHashMap<>();
        ex.put("a", "memo");
        ex.put(97, "memo012");
    }
}
