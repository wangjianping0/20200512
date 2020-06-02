package com.example.firstdemo.test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author joonwhee
 * @date 2019/7/6
 */
public class VolatileTest {

//    public static volatile int race = 0;
    public static AtomicInteger race = new AtomicInteger(0);




    private static final int THREADS_COUNT = 20;

    private static CountDownLatch countDownLatch = new CountDownLatch(THREADS_COUNT);

    public static void increase() {
//        race++;
        race.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
        long start=System.nanoTime();
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                    countDownLatch.countDown();
                }
            });
            threads[i].start();
        }
        countDownLatch.await();
        System.out.println(race);
        long end=System.nanoTime();
        System.out.println("耗时:"+(end-start)+"纳秒");
    }
}