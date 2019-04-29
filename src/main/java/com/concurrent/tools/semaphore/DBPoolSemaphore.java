package com.concurrent.tools.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 演示Semaphore用法，一个数据库连接池的实现.
 */
public class DBPoolSemaphore {
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

    // 信号量[可以使用-已经使用的]
    //acquire 方法->从这个信号量获得一个许可证，阻塞直到一个许可证被阻塞为止可用
    public static Semaphore canUse, used;

    public DBPoolSemaphore() {
        this.canUse = new Semaphore(10);//许可证的初始化值10
        this.used = new Semaphore(0);//
    }

    /**
     * 释放连接.
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                System.out.println("当前有" + canUse.getQueueLength() + "个线程等待数据库连接!!"
                    + "可用连接数：" + canUse.availablePermits());
                //确定之前放出去的信号量数量不能超了，是否还存在已经颁发的许可证
                // 【未还的数量还有才能还】
                used.acquire();
                synchronized (pool) {
                    pool.addLast(connection);
                }
                //释放许可证，将其返回到信号量。
                canUse.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            //放入一个许可证,控制可用许可证的数量，代表已经颁发了一个许可证
            used.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
