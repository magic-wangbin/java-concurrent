package com.concurrent.aqs.extend;


import com.util.SleepTools;

import java.util.concurrent.locks.Lock;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * <p>
 * 类说明：
 */
public class TestReenterSelfLock {

    static final Lock lock = new ReenterSelfLock();

    public int reenter(int x) {
        lock.lock();
        int y = -1;
        try {
            System.out.println(Thread.currentThread().getName() + ":递归层级:" + x);
            y = x - 1;
            if (y == 0) {
                System.out.println("y==0");
                return 1000;
            } else {
                reenter(y);
            }
        } finally {
            System.out.println("finally!y=" + y);
            lock.unlock();
        }
        return -1000;
    }

    public void test() {
        class Worker extends Thread {
            public void run() {
                System.out.println(Thread.currentThread().getName());
                SleepTools.second(1);
                int s = reenter(3);
                System.out.println(s);
            }
        }
        // 启动3个子线程
        for (int i = 0; i < 100; i++) {
            Worker w = new Worker();
            w.start();
        }
        // 主线程每隔1秒换行
//        for (int i = 0; i < 100; i++) {
//        	SleepTools.second(1);
//        }
    }

    public static void main(String[] args) {
        TestReenterSelfLock testMyLock = new TestReenterSelfLock();
        testMyLock.test();
    }
}
