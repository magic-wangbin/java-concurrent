package com.concurrent.tools;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 工作密取.
 */
public class SecretWorkFetch2 {


    public static class Work implements Runnable {

        private int workId;
        private Long workThreadId;

        public Work(Long workThreadId, int workId) {
            this.workId = workId;
            this.workThreadId = workThreadId;
        }


        // ----------------------------
        @Override
        public void run() {
            Long threadId = Thread.currentThread().getId();
            //如果当前线程和生成work的线程不是同一个
            if (threadId != workThreadId) {
                System.out.println(threadId + "=========正在完成=========" + workThreadId + "=======的任务======jobId=" + workId + "=====");
            } else {
                System.out.println(threadId + "=" + workThreadId + ",执行自己的任务" + workId);
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

        private AtomicInteger workId;

        public ConsumerAndProducer(LinkedBlockingDeque<Work> deque, LinkedBlockingDeque<Work> otherWork, AtomicInteger workId) {
            this.deque = deque;
            this.otherWork = otherWork;
            this.workId = workId;
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
                            deque.putLast(new Work(Thread.currentThread().getId(), workId.getAndIncrement()));
                        }
                    }
                    //System.out.println(" yss otherWork.isEmpty(): "+otherWork.isEmpty());
                    if (deque.isEmpty()) {
                        if (!otherWork.isEmpty()) {
//                            System.out.println("otherWork is run:！！！！！！！！！！！！！！！！！");
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
        //new Thread(new ConsumerAndProducer(deque, other,new AtomicInteger(0))).start();
        new Thread(new ConsumerAndProducer(deque, other,new AtomicInteger(0))).start();

       // new Thread(new ConsumerAndProducer(other, deque,new AtomicInteger(0))).start();
        new Thread(new ConsumerAndProducer(other, deque,new AtomicInteger(0))).start();

    }

}
