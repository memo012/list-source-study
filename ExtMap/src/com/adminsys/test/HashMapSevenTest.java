package com.adminsys.test;

import com.adminsys.ext.Ext7HashMap;

import java.util.HashMap;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-14 12-38
 **/

public class HashMapSevenTest {
    public static void main(String[] args) {
        Integer b = new Integer(97);
        String a = "a";
        Ext7HashMap<Object, String> hashMap = new Ext7HashMap<>();
        HashMap<Object, String> hashMap1 = new HashMap<>();
        hashMap1.put(a, "memo");
        hashMap.put(b, "memo012");
    }
}
