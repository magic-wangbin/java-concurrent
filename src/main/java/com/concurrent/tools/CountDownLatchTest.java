package com.concurrent.tools;

import com.util.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 * 类说明：演示CountDownLatch用法，
 * 共5个初始化子线程，6个闭锁扣除点，扣除完毕后，主线程和业务线程才能继续执行
 */
public class CountDownLatchTest {

    public static final CountDownLatch countDownLatch = new CountDownLatch(6);

    /**
     * 该任务一次latch减2
     */
    public static class LatchReduceTwoTask implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "latch reduce two begin .... ....");
            countDownLatch.countDown();
            System.out.println("执行一定的业务代码：休眠100ms");
            SleepTools.ms(100);
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + "latch reduce two end .... ....");
        }
    }


    public static class LatchReduceOneTask implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "latch reduce one begin .... ....");
            System.out.println("执行一定的业务代码：休眠200ms");
            SleepTools.ms(200);
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + "latch reduce one end .... ....");
        }
    }

    public static class BusinessTask implements Runnable {


        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "业务线程开始执行自己的逻辑begin .... ....");
            SleepTools.second(1);
            System.out.println(Thread.currentThread().getName() + "业务线程执行自己的逻辑end .... ....");
        }
    }

    public static void main(String[] args) {
        System.out.println("latch count down test begin .... ....");
        new Thread(new LatchReduceTwoTask()).start();

        //业务线程开始
        new Thread(new BusinessTask()).start();        //减少4个

        //阻塞5s后再往下执行
        for (int i = 1; i < 6; i++) {
            System.out.println("阻塞倒计时：" + i);
            SleepTools.second(1);
        }

        System.out.println("=================================");


        for (int i = 0; i <= 3; i++) {
            new Thread(new LatchReduceOneTask()).start();
        }


        try {
            System.out.println("主线程进行阻塞等待！！！！");
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + "主线程开始执行自己的逻辑begin.... ....");
            SleepTools.second(1);
            System.out.println(Thread.currentThread().getName() + "主线程执行自己的逻辑end.... ....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
