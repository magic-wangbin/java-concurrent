package com.concurrent.cus.queue;

import com.util.SleepTools;

import java.util.Random;

public class QueueTest {
    public static void main(String[] args) {
        QueueImplByLock<Integer> queue = new QueueImplByLock<>();

        //
        new Thread(new ProduceWork(queue)).start();
        SleepTools.second(1);
        for (int i = 0; i < 3; i++) {
            new Thread(new ConsumeWork(queue)).start();
        }
    }

    public static class ProduceWork implements Runnable {

        private QueueImplByLock<Integer> queue;

        public ProduceWork(final QueueImplByLock<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int i = 0;
            Random random = new Random();
            while (i <= 100) {
                int time = random.nextInt(2);
                int number = random.nextInt(100);
                SleepTools.second(time);
                try {
                    queue.put(number);
                    System.out.println(Thread.currentThread().getName()
                        + "sleep:" + time
                        + ",放入元素" + number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ConsumeWork implements Runnable {
        private QueueImplByLock<Integer> queue;

        public ConsumeWork(final QueueImplByLock<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int i = 0;
            Random random = new Random();
            int time = random.nextInt(2);
            int number = random.nextInt(100);
            while (i <= 100) {
                SleepTools.second(time);
                try {
                    Integer t = queue.take();
                    System.out.println(Thread.currentThread().getName()
                        + "sleep:" + time
                        + ",获取元素" + t);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
