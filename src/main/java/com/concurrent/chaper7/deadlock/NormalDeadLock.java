package com.concurrent.chaper7.deadlock;

/**
 * 写一个死锁.
 */
public class NormalDeadLock {
    //定义两个线程，两把锁，每个线程依次去拿锁，顺序相反
    private static Object lockFirst = new Object();

    private static Object lockTwo = new Object();

    //先拿第一个锁，再拿第二个锁
    private static void fisrtToSecond() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (lockFirst) {
            System.out.println(threadName + " get 1st");
            Thread.sleep(100);
            synchronized (lockTwo) {
                System.out.println(threadName + " get 2nd");
            }
        }
    }

    //先拿第二个锁，再拿第一个锁
    private static void SecondToFisrt() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (lockTwo) {
            System.out.println(threadName + " get 2nd");
            Thread.sleep(100);
            synchronized (lockFirst) {
                System.out.println(threadName + " get 1st");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fisrtToSecond();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            SecondToFisrt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
