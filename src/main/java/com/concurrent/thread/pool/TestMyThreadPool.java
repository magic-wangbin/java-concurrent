package com.concurrent.thread.pool;

import java.util.Random;

/**
 * 类说明：测试自定义线程池实现
 */
public class TestMyThreadPool {
    public static void main(String[] args) throws InterruptedException {
//         创建3个线程的线程池
        CusThreadPool t = new CusThreadPool();
        t.execute(new MyTask("testA"));
        t.execute(new MyTask("testB"));
        t.execute(new MyTask("testC"));
        t.execute(new MyTask("testD"));
        t.execute(new MyTask("testE"));
        t.execute(new MyTask("testA1"));
        t.execute(new MyTask("testB1"));
        t.execute(new MyTask("testC1"));
        t.execute(new MyTask("testD1"));
        t.execute(new MyTask("testE1"));
        System.out.println(t);
        Thread.sleep(10000);
        t.destroy();// 所有线程都执行完成才destory
        System.out.println(t);
    }

    // 任务类
    static class MyTask implements Runnable {

        private String name;
        private Random r = new Random();

        public MyTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {// 执行任务
            //int x =10;
            try {
                Thread.sleep(r.nextInt(1000) + 2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " sleep InterruptedException:"
                    + Thread.currentThread().isInterrupted());
            }
            System.out.println("任务 " + name + " 完成");
        }
    }
}
