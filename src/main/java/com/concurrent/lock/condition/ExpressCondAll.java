package com.concurrent.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 快递-唤醒测试对象[里程或地址改变唤醒业务处理].
 */
public class ExpressCondAll {
    public final static String CITY = "ShangHai";
    private int km;/*快递运输里程数*/
    private String site;/*快递到达地点*/

    public ExpressCondAll(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /**
     * 定义锁
     **/
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    /**
     * 修改里程.
     */
    public void changeKm() {
        lock.lock();
        try {
            this.km = 101;
            //
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 修改里程唤醒(数据库修改).
     * 当快递的里程数大于100时更新数据库
     */
    public void waitKm() {
        lock.lock();
        try {
            while (km <= 100) {
                try {
                    condition.await();
                    System.out.println("Check km thread["
                            + Thread.currentThread().getId()
                            + "] is be notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("the Km is " + this.km + ",I will change db");
        } finally {
            lock.unlock();
        }
    }

    /**
     * 地址修改.
     */
    public void changeSite() {
        lock.lock();
        try {
            this.site = "BeiJing";
            condition.signalAll();//通知其他在锁上等待的线程
        } finally {
            lock.unlock();
        }
    }

    /**
     * 地址修改等待.
     */
    public void waitSiteChange() {
        lock.lock();
        try {
            while (this.site.equals(CITY)) {
                //地址未改变
                try {
                    condition.await();
                    //被唤醒一次
                    System.out.println("check Site thread[" + Thread.currentThread().getName()
                            + "] is be notify");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //
            System.out.println("the site is " + this.site + ",I will call user");
        } finally {
            lock.unlock();
        }
    }
}
