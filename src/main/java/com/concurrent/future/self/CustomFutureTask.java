package com.concurrent.future.self;

import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class CustomFutureTask<V> implements Runnable, Future<V> {

    private final Sync sync;

    public CustomFutureTask(Callable<V> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        this.sync = new Sync(callable);
    }


    /**
     * 使用AQS实现.
     */
    public final class Sync extends AbstractQueuedSynchronizer {
        /**
         * 表示任务正在执行.
         */
        private static final int RUNNING = 1;
        /**
         * 表示任务已经运行完毕.
         */
        private static final int FINISH = 2;
        /**
         * 执行结果.
         */
        private V result;

        /**
         * 任务.
         */
        private Callable<V> callable;

        public Sync(Callable<V> callable) {
            super();
            this.callable = callable;
        }

        //
        //任务没完成，让get结果的线程全部进入同步队列
        //acquireShared方法返回了，说明可以拿结果了，直接返回结果
        V innerGet() {
            //拿锁
            acquireShared(0);
            return result;
        }

        /*对任务的状态进行变化，设置执行结果，并唤醒所有等待结果的线程*/
        void innerSetState(V v) {
            System.out.println("innerSetState");
            for (; ; ) {
                System.out.println("innerSetState---->for循环");
                // 获取任务执行状态。
                int s = getState();
                if (s == FINISH) {
                    // 如果任务已经执行完毕，退出。
                    return;
                }
                // 尝试将任务状态设置为执行完成。
                if (compareAndSetState(s, FINISH)) {
                    // 设置执行结果。
                    result = v;
                    // 释放控制权。
                    releaseShared(0);
                    return;
                }
            }
        }

        protected boolean tryReleaseShared(int releases) {
            return true;
        }

        //任务没完成，返回-1，让get结果的线程全部进入同步队列
        //返回1，可以让所有在同步队列上等待的线程一一去拿结果
        protected int tryAcquireShared(int acquires) {
            return this.getState() == FINISH ? 1 : -1;
        }

        void innerRun() {
            if (this.compareAndSetState(0, RUNNING)) {
                System.out.println("设置当前线程的状态........");
                if (this.getState() == RUNNING) {//再检查一次，双重保障
                    System.out.println("设置当前线程的状态........ok");
                    try {
                        /*将call()方法的执行结果赋值给Sync中的result*/
                        this.innerSetState(this.callable.call());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    /*如果不等于RUNNING，表示被取消或者是抛出了异常。这时候唤醒调用get的线程。*/
                    this.releaseShared(0);
                }
            }
        }

    }

    @Override
    public void run() {
        sync.innerRun();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return sync.innerGet();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
