package com.adminsys.test;

import com.adminsys.ext.ExtArrayList;
import com.adminsys.ext.ExtCopyOnWriteArrayList;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-11 11-47
 **/

public class Main {
    public static void main(String[] args){
        ExtArrayList<String> list = new ExtArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("33--"+i);
        }
        list.add("34");
        list.remove(0);
        System.out.println(list.get(0));
        ExtCopyOnWriteArrayList<String> c = new ExtCopyOnWriteArrayList<String>();
        c.add("33");
        System.out.println(c.get(0));

    }
}
