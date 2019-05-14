package com.concurrent.thread.pool;

import com.util.SleepTools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 自定义一个线程池.
 */
public class CusThreadPool {
    /**
     * 说明：
     * 1: 设置线程的数量
     * 2：设置任务队列
     * 3：任务的提交,任务提交到queue
     * 4：任务的执行
     * 5：线程的关闭
     **/

    //缺省线程数
    private static final int DEFAULT_THREAD_NUM = 5;
    private static final int DEFAULT_QUEUE_SIZE = 100;

    private int queueSize;
    private int threadNum;

    private List<Thread> threadList = new ArrayList<>();

    private static BlockingQueue<Runnable> queue;

    public CusThreadPool() {
        this(DEFAULT_THREAD_NUM, DEFAULT_QUEUE_SIZE);
    }

    /**
     * 初始化线程.
     *
     * @param threadNumber 线程的数量.
     * @param queueSize    可以缓存的任务数.
     */
    public CusThreadPool(int threadNumber, int queueSize) {

        this.threadNum = (threadNumber > 0) ? threadNumber : DEFAULT_THREAD_NUM;
        this.queueSize = (queueSize > 0) ? queueSize : DEFAULT_QUEUE_SIZE;

        queue = new LinkedBlockingDeque<>(this.queueSize);
        //初始化线程
        for (int i = 0; i < threadNumber; i++) {
            Thread thread = new InnnerThread(i);
            threadList.add(thread);
            thread.start();
        }
    }

    public static class InnnerThread extends Thread {

        public InnnerThread(int threadIndex) {
            super("CUS_THREAD_POOL_" + String.valueOf(threadIndex));
        }

        @Override
        public void run() {
            SleepTools.second(1);
            Runnable runnable;
            try {
                while (!isInterrupted()) {
                    runnable = queue.take();
                    if (runnable != null) {
                        System.out.println(Thread.currentThread().getName()
                            + " ready execute"
                            + ((TestMyThreadPool.MyTask) runnable).getName());
                        runnable.run();
                    }
                    runnable = null;
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    public void execute(Runnable runnable) {
        try {
            queue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        System.out.println("线程开始停止！");
        for (Thread thread : threadList) {
            thread.interrupt();
        }
    }

    @Override
    public String toString() {
        return "WorkThread number:" + threadNum
            + " wait task number:" + queue.size();
    }

}
