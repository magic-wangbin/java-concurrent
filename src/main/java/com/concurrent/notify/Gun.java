package com.concurrent.notify;

import com.util.SleepTools;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Gun {
    public static final int DEFAULT_NUM = 20;//默认20

    public static final LinkedList<Bullet> pool = new LinkedList<>();

    public static final Object object = new Object();

    public Gun() {
        for (int i = 0; i < DEFAULT_NUM; i++) {
            pool.addLast(new Bullet());
        }
    }

    /**
     * 插入.
     */
    public void put(AtomicInteger inputCount) {
        synchronized (pool) {
            while (!Thread.currentThread().isInterrupted()) {
                while (pool.size() == DEFAULT_NUM ) {
                    try {
                        System.out.println("bullet is Full" + Thread.currentThread().isInterrupted());
                        pool.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("安全的中断当前线程！");
                        this.notifyAll();
                    }
                }

                SleepTools.ms(100);
                pool.addLast(new Bullet());
                inputCount.getAndIncrement();
                System.out.println("put a num success" + Thread.currentThread().isInterrupted());
                pool.notifyAll();
            }
        }
    }

    public Bullet take() {
        synchronized (pool) {
            while (pool.isEmpty()) {
                try {
                    pool.wait();
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
            System.out.println("take a num success");
            pool.notifyAll();
            return pool.removeFirst();
        }

    }
}
