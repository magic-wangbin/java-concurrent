package com.concurrent.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {

    //默认最大容量10个连接
    public static final DBPool pool = new DBPool(10);

    //
    public static final CountDownLatch end = new CountDownLatch(50);

    //线程的数量
    public static final int THREAD_NUMBER = 50;

    //每个线程执行的次数
    public static final int PER_THREAD_NUMBER = 20;

    //计数器：统计可以拿到连接的线程
    public static AtomicInteger got = new AtomicInteger();

    //计数器：统计没有拿到连接的线程
    public static AtomicInteger notGot = new AtomicInteger();

    public static long mills = 1000;

    public static class Work implements Runnable {

        private int preThreadNumber;

        public Work(int preThreadNumber) {
            this.preThreadNumber = preThreadNumber;
        }

        /**
         * 执行具体的任务.
         */
        @Override
        public void run() {
            while (preThreadNumber > 0) {
                //
                Connection connection = null;//
                try {
                    connection = pool.fetchConnection(mills);
                    if (connection == null) {
                        //没有拿到
                        notGot.getAndIncrement();
//                        System.out.println(Thread.currentThread().getName()
//                            + "等待超时!");
                        //System.out.println(Thread.currentThread().getName() + "等待超时!");
                    } else {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            got.getAndIncrement();
                            pool.releaseConnection(connection);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //循环一次减一次
                    preThreadNumber--;
                }
            }
            end.countDown();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(new Work(PER_THREAD_NUMBER)).start();
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("总共尝试了: " + (THREAD_NUMBER * PER_THREAD_NUMBER));
            System.out.println("拿到连接的次数：  " + got);
            System.out.println("没能连接的次数： " + notGot);
        }
    }
}
