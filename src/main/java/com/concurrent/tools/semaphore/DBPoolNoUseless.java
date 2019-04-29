package com.concurrent.tools.semaphore;

import com.util.SleepTools;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class DBPoolNoUseless {
    // 连接池最大可用数
    public static final int POOL_MAX_USE_NUM = 10;

    // 连接池容器
    public static LinkedList<Connection> pool = new LinkedList<>();

    // 初始化固定的连接
    static {
        for (int i = 0; i < POOL_MAX_USE_NUM; i++) {
            pool.add(new SqlConnectImpl());
        }
    }


    public static Semaphore canUse;

    public DBPoolNoUseless() {
        this.canUse = new Semaphore(10);//许可证的初始化值10
    }

    /**
     * 释放连接.
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            System.out.println("当前有" + canUse.getQueueLength() + "个线程等待数据库连接!!"
                + "可用连接数：" + canUse.availablePermits());
            //确定之前放出去的信号量数量不能超了，是否还存在已经颁发的许可证
            // 【未还的数量还有才能还】
            synchronized (pool) {
                pool.addLast(connection);
            }
            //释放许可证，将其返回到信号量。
            canUse.release();
        }
    }

    /**
     * 获取一个连接.
     *
     * @return
     */
    public Connection fetchConnection() {
        Connection connection = null;
        try {

            //从信号量中获取一个许可证
            canUse.acquire();
            synchronized (pool) {
                connection = pool.removeFirst();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static DBPoolNoUseless dbPoolNoUseless = new DBPoolNoUseless();

    private static class BusiThread extends Thread {
        @Override
        public void run() {
            Random r = new Random();//让每个线程持有连接的时间不一样
            long start = System.currentTimeMillis();
            System.out.println("Thread_" + Thread.currentThread().getId()
                + "_获取数据库连接共耗时【" + (System.currentTimeMillis() - start) + "】ms.");
            SleepTools.ms(100 + r.nextInt(100));//模拟业务操作，线程持有连接查询数据
            System.out.println("查询数据完成，归还连接！");
            dbPoolNoUseless.releaseConnection(new SqlConnectImpl());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Thread thread = new BusiThread();
            thread.start();
        }
    }
}
