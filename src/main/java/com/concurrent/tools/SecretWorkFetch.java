package com.concurrent.tools;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 工作密取.
 */
public class SecretWorkFetch {

    public static class Work implements Runnable {
        public static Object object = new Object();
        private static int count = 0;
        public final int jobId;
        private long putThread;

        public Work() {
            synchronized (object) {
                jobId = count++;
            }
        }

        public long getPutThread() {
            return putThread;
        }

        public void setPutThread(long putThread) {
            this.putThread = putThread;
        }


        // ----------------------------
        @Override
        public void run() {
            Long threadId = Thread.currentThread().getId();
            //如果当前线程和生成work的线程不是同一个
            if (threadId != putThread) {
                System.out.println("线程" + threadId + "=========正在完成=========" + putThread + "=======的任务======jobId=" + jobId + "=====");
            } else {
                System.out.println("线程" + threadId + "=" + putThread + ",执行自己的任务" + jobId);
            }
        }

    }

    /**
     * 生产者和消费者线程.
     */
    private static class ConsumerAndProducer implements Runnable {
        private Random random = new Random();
        //自己的任务队列
        private final LinkedBlockingDeque<Work> deque;
        //其他的任务队列
        private final LinkedBlockingDeque<Work> otherWork;

        public ConsumerAndProducer(LinkedBlockingDeque<Work> deque, LinkedBlockingDeque<Work> otherWork) {
            this.deque = deque;
            this.otherWork = otherWork;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(200);
                    //往队列塞任务
                    if (random.nextBoolean()) {
                        int count = random.nextInt(5);
                        for (int i = 0; i < count; i++) {
                            Work w = new Work();
                            w.setPutThread(Thread.currentThread().getId());
                            deque.putLast(w);
                        }
                    }
                    //System.out.println(" yss otherWork.isEmpty(): "+otherWork.isEmpty());
                    if (deque.isEmpty()) {
                        if (!otherWork.isEmpty()) {
                            System.out.println("otherWork is run:！！！！！！！！！！！！！！！！！");
                            otherWork.takeLast().run();
                        }

                    } else {
                        deque.takeFirst().run();
                    }
                } catch (InterruptedException e) {

                }
            }


        }


    }


    public static void main(String[] args) {
        LinkedBlockingDeque<Work> deque = new LinkedBlockingDeque<Work>();
        LinkedBlockingDeque<Work> other = new LinkedBlockingDeque<Work>();
        new Thread(new ConsumerAndProducer(deque, other)).start();
        new Thread(new ConsumerAndProducer(deque, other)).start();

        new Thread(new ConsumerAndProducer(other, deque)).start();
        new Thread(new ConsumerAndProducer(other, deque)).start();

    }

}
