package com.concurrent.tools.semaphore;

import com.util.SleepTools;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 信号量测试.
 */
public class DBPoolSemaphoreTest {

    // 定义50个线程，并发访问，每个线程访问20次
    public static CountDownLatch countDownLatch = new CountDownLatch(50);
    public static final int FETCH_NUMBER = 20;

    //拿到连接池的数量
    public static AtomicInteger got = new AtomicInteger(0);

    public static DBPoolSemaphore dbPoolSemaphore = new DBPoolSemaphore();


    public static class FetchConnetionTask implements Runnable {
        @Override
        public void run() {
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < FETCH_NUMBER; i++) {
                Connection connection = dbPoolSemaphore.fetchConnection();
                if (connection != null) {
                    System.out.println(Thread.currentThread().getName() + "获得一个连接");
                    got.getAndIncrement();
                    // 使用连接100ms
                    SleepTools.ms(100);
                    //使用后放回
                    dbPoolSemaphore.releaseConnection(connection);
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(new FetchConnetionTask()).start();
        }
        SleepTools.second(20);
        System.out.println("获取的连接池总数量" + got);
    }
}
