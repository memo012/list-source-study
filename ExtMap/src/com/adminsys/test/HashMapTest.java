package com.adminsys.test;

import com.adminsys.ext.ExtArrayListHashMap;
import com.adminsys.ext.ExtLinkedListHashMap;
import com.adminsys.extEight.ExtHashMap;

import java.util.HashMap;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-13 04-54
 **/

public class HashMapTest {
    public static void main(String[] args) {
        ExtHashMap<Object, String> ex = new ExtHashMap<>();
        ex.put("a", "dd");
        ex.put(97, "aa");
//        System.out.println(ex.get(97));
    }
}
