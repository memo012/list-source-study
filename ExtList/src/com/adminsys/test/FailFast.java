package com.adminsys.test;

import java.util.ArrayList;

/**
 * @Author: qiang
 * @Description:
 * @Create: 2019-12-12 07-11
 **/

public class FailFast {
    // 定义一个全局的集合存放 存在于高并发的情况下线程安全问题
    private static ArrayList<String> list = new ArrayList<>();
    public static void main(String[] args) {
        /*
        Fail-Fast 是我们Java集合框架为了解解决集合中结构发生改变的时候，快速失败的机制
         */
        new FailFast().startRun();
    }

    private void startRun(){
        new Thread(new ThreadOne()).start();
        new Thread(new ThreadTwo()).start();
    }

    private class ThreadOne implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                list.add("i:" + i);
                print();
            }
        }
    }

    private class ThreadTwo implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                list.add("i:" + i);
                print();
            }
        }
    }

    public static void print(){
        list.forEach((t)->System.out.println("t:"+t));
    }

}
