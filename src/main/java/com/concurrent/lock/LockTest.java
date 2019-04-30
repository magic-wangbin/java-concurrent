package com.concurrent.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock测试.
 */
public class LockTest {

    static CountDownLatch countDownLatch = new CountDownLatch(2);

    private Lock lock = new ReentrantLock();
    //要锁的变量
    private int age = 100;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void increase() {
        for (int i = 0; i < 10000; i++) {
            try {
                lock.lock();
                age++;
            } finally {
                //虽然守护线程中finally不一定起作用
                // 但是守护线程退出所有得资源自动回收
                lock.unlock();
            }
        }
        countDownLatch.countDown();
    }

    public void reduce() {

        for (int i = 0; i < 10000; i++) {
            try {
                lock.lock();
                age--;
            } finally {
                lock.unlock();
            }
        }
        countDownLatch.countDown();
    }

    public static class IncreaseTask implements Runnable {

        private LockTest lockTest;

        public IncreaseTask(LockTest lockTest) {
            this.lockTest = lockTest;
        }

        @Override
        public void run() {
            lockTest.increase();
        }
    }

    public static class ReduceTask implements Runnable {

        private LockTest lockTest;

        public ReduceTask(LockTest lockTest) {
            this.lockTest = lockTest;
        }

        @Override
        public void run() {
            lockTest.reduce();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        LockTest lockTest = new LockTest();

        new Thread(new IncreaseTask(lockTest)).start();
        new Thread(new ReduceTask(lockTest)).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(lockTest.getAge());
    }

}

