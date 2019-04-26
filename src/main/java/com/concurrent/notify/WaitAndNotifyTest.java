package com.concurrent.notify;

import com.util.SleepTools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class WaitAndNotifyTest {

    public static final Gun pool = new Gun();

    //计数器
    public static AtomicInteger got = new AtomicInteger(0);

    public static AtomicInteger input = new AtomicInteger(0);

    //
    public static final CountDownLatch end = new CountDownLatch(50);


    public static void main(String[] args) {

        Map<String, Thread> map = new ConcurrentHashMap<>();


        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new ProductWork(input, got));
            map.put("thread" + i, thread);
            thread.start();
        }


        //50个结束后自动中断
        for (int i = 0; i < 50; i++) {
            new Thread(new ConsumerWork(got)).start();
        }


        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("总共获取到的次数：" + got);
        while (input.get() < 50) {
            SleepTools.second(1);
        }
        //中断线程
        for (Thread thread : map.values()) {
            thread.interrupt();
        }
        System.out.println("总共获input的次数：" + input);

    }

    /**
     * 消费者
     */
    public static class ConsumerWork implements Runnable {
        private AtomicInteger got;

        public ConsumerWork(AtomicInteger got) {
            this.got = got;
        }

        /**
         * 执行具体的任务.
         */
        @Override
        public void run() {
            pool.take();
            got.getAndIncrement();
            end.countDown();
        }
    }

    /**
     * 生产者
     */
    public static class ProductWork implements Runnable {

        private AtomicInteger inputCount;

        public ProductWork(AtomicInteger inputCount, AtomicInteger got) {
            this.inputCount = inputCount;
        }

        /**
         * 执行具体的任务.
         */
        @Override
        public void run() {
            pool.put(inputCount);
        }
    }
}

