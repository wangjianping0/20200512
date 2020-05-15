package com.example.firstdemo.test;

/**
 * 通过接口构造两个线程对象t1、t2，t1的线程名为"线程1"，t2的线程名为"线程2"，每个线程分别运行5次。每运行一次都要输出当前线程的名称。
 */
class SiTh extends Thread {

    public void run()  {
        for (int i = 0; i < 5; i++) {
            System.out.println(i + " " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }
}

public class MyTr {
    public static void main(String args[]) {
        Runnable r = new SiTh() ;
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
    }
}