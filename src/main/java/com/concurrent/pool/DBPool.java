package com.concurrent.pool;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现一个简单的连接池.
 */
public class DBPool {

    private static final int DEFAULT_INITIAL_SIZE = 20;

    private static AtomicInteger nofifyNum = new AtomicInteger();

    // 1: 创建一个连接池容器
    public static LinkedList<Connection> pool = new LinkedList<>();

    // 2: 初始化固定大小的连接池容器
    public DBPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    public DBPool() {
        for (int i = 0; i < DEFAULT_INITIAL_SIZE; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    /**
     * 3: 获取连接
     *
     * @param mills 超时时间
     * @return
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        //获取连接之前首先获得锁
        synchronized (pool) {
            //永不超时
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long futureTime = System.currentTimeMillis() + mills;
                long waitingTime = mills;
                // 连接池没有连接，存在等待时间
                while (pool.isEmpty() && waitingTime > 0) {
                    pool.wait(waitingTime);
                    //唤醒一次执行一次，自己等待时间到了执行一次，nofifyNum每次获取Connection执行次数>1
                    nofifyNum.getAndIncrement();
                    System.out.println(Thread.currentThread().getName() + "唤醒进行的操作........" + nofifyNum + "剩余时间" + waitingTime);
                    //唤醒
                    waitingTime = futureTime - System.currentTimeMillis();
                }
                //说明：waitingTime>0 获取到连接返回连接
                //说明：waitingTime<=0 如果能拿到也返回【只有一次机会】
                if (waitingTime <= 0 && !pool.isEmpty()) {
                    System.out.println("waitingTime=" + waitingTime + "pool.isEmpty()=" + pool.isEmpty());
                }
                Connection connection = null;
                if (!pool.isEmpty()) {
                    connection = pool.removeFirst();
                }
                return connection;

            }

        }
    }

    // 4: 释放连接
    public void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        synchronized (pool) {
            pool.addLast(connection);
            pool.notifyAll();
        }
    }
}
