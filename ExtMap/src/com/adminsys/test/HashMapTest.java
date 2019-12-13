package com.adminsys.test;

import com.adminsys.ext.ExtArrayListHashMap;
import com.adminsys.ext.ExtLinkedListHashMap;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-13 04-54
 **/

public class HashMapTest {
    public static void main(String[] args) {
//        new HashMap<>()
        ExtLinkedListHashMap<Object, String> ex = new ExtLinkedListHashMap<>();
        ex.put("a", "dd");
        ex.put(97, "aa");
        System.out.println(ex.get(97));
    }
}
