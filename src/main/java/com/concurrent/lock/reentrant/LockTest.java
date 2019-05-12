package com.concurrent.lock.reentrant;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock测试.
 */
public class LockTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    private Lock lock = new ReentrantLock();
    //要锁的变量
    private int age = 100;

    private int getAge() {
        return age;
    }

    private void increase() {
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

    private void reduce() {

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

    private static class IncreaseTask implements Runnable {

        private LockTest lockTest;

        private IncreaseTask(LockTest lockTest) {
            this.lockTest = lockTest;
        }

        @Override
        public void run() {
            lockTest.increase();
        }
    }

    private static class ReduceTask implements Runnable {

        private LockTest lockTest;

        private ReduceTask(LockTest lockTest) {
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

        countDownLatch.await();

        System.out.println(lockTest.getAge());
    }

}

