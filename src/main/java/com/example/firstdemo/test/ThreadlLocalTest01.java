package com.example.firstdemo.test;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadlLocalTest01 {
    private String content;

    private String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {
//        test();
//        syntest();
        reentest();
    }

    public static void test() {
        //实例化我们的对象，然后传入对应的值
        ThreadlLocalTest01 t1 = new ThreadlLocalTest01();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    t1.setContent(Thread.currentThread().getName() + "");
                    System.out.println(Thread.currentThread().getName() + "的数据为：" + t1.getContent());
                }
            });
            thread.start();
        }
    }

    public static void syntest() {
        //实例化我们的对象，然后传入对应的值
        ThreadlLocalTest01 t1 = new ThreadlLocalTest01();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (ThreadlLocalTest01.class) {
                        t1.setContent(Thread.currentThread().getName() + "");
                        System.out.println(Thread.currentThread().getName() + "的数据为：" + t1.getContent());
                    }
                }
            });
            thread.start();
        }
    }

    public static void reentest() {
        //实例化我们的对象，然后传入对应的值
        ThreadlLocalTest01 t1 = new ThreadlLocalTest01();
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    t1.setContent(Thread.currentThread().getName() + "");
                    System.out.println(Thread.currentThread().getName() + "的数据为：" + t1.getContent());
                    lock.unlock();
                }
            });
            thread.start();
        }
    }
}
