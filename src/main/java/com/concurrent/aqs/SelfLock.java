package com.concurrent.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义独占锁，不可重入.
 */
public class SelfLock implements Lock {

    // 静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 获得锁.
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            //拿取锁是否成功
            Boolean flag = compareAndSetState(0, 1);
            if (flag) {
                // 设置当前线程为锁的持有者
                setExclusiveOwnerThread(Thread.currentThread());
            }
            return flag;
        }

        /**
         * 释放锁.
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            //先查看状态
            int status = getState();
            if (status == 0) {
                //非法的监控状态
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return super.tryReleaseShared(arg);
        }

        /**
         * 判断处于占用状态。
         *
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }


    //------------------------lock接口的实现--------------------------

    // 仅需要将操作代理到Sync上即可
    private final Sync sync = new Sync();

    /**
     * 获取锁。
     * 如果锁不可用，出于线程调度目的，将禁用当前线程，并且在获得锁之前，该线程将一直处于休眠状态。
     */
    @Override
    public void lock() {

        sync.acquire(1);
        System.out.println();
        System.out.println(Thread.currentThread().getName() + " ready get lock");
        System.out.println(Thread.currentThread().getName() + " already got lock");
    }

    /**
     * 如果当前线程未被中断，则获取锁。
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 仅在调用时锁为空闲状态才获取该锁。如果锁可用，则获取锁，并立即返回值 true。如果锁不可用，则此方法将立即返回值 false。
     *
     * @return
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }


    /**
     * 如果锁在给定的等待时间内空闲，并且当前线程未被中断，则获取锁。
     *
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    /**
     * 释放锁。
     * 在等待条件前，锁必须由当前线程保持。
     * 调用 Condition.await() 将在等待前以原子方式释放锁，并在等待返回前重新获取锁。
     */
    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName() + " ready release lock");

        System.out.println(Thread.currentThread().getName() + " already released lock");

        sync.release(1);
    }

    /**
     * 返回绑定到此 Lock 实例的新 Condition 实例。
     *
     * @return
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    // ---------------------------------
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }
}
