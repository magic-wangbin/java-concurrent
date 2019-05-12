package com.concurrent.aqs;

import com.util.SleepTools;

import java.util.concurrent.locks.Lock;

/**
 * 自定义独占锁测试.
 */
public class SelfLockTest {
    private static Lock lock = new SelfLock();

    public void reenter(int x) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":递归层级:" + x);
            int y = x - 1;
            if (y == 0) return;
            else {
                reenter(y);
            }
//            for (int i = 0; i < x; i++) {
//                System.out.println(Thread.currentThread().getName() + "这个线程在执行！");
//            }

        } finally {
            lock.unlock();
        }

    }

    public void test() {
        class Worker extends Thread {
            public void run() {
                System.out.println(Thread.currentThread().getName());
                SleepTools.second(1);
                reenter(3);
            }
        }
        // 启动3个子线程
        for (int i = 0; i < 3; i++) {
            Worker w = new Worker();
            w.start();
        }
        // 主线程每隔1秒换行
//        for (int i = 0; i < 100; i++) {
//            SleepTools.second(1);
//        }
    }

    public static void main(String[] args) {
        SelfLockTest testMyLock = new SelfLockTest();
        testMyLock.test();
    }
}
