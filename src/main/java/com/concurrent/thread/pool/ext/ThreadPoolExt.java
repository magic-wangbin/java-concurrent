package com.concurrent.thread.pool.ext;

import com.util.DateFormatUtil;
import com.util.SleepTools;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExt {
    private static final int CORE_POOL_SIZE = 2;
    private static final int MAXIMUM_POOL_SIZE = 4;
    private static final int KEEP_ALIVI_TIME_SECONDS = 3;
    private static final int QUEUE_CAPACITY = 10;

    public static void main(String[] args) {

        ExecutorService executorService = new CusThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVI_TIME_SECONDS, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY),
            new DaemonThreadFactory(),
            new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        for (int i = 0; i <= 6; i++) {
            Worker worker = new Worker("worker " + i);
            System.out.println("A new task has been added : " + worker.getName());
            executorService.execute(worker);
        }

        SleepTools.second(1);
        //executorService.shutdown();
    }

    public static class Worker implements Runnable {
        private String taskName;
        private Random r = new Random();

        public Worker(String taskName) {
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }

        @Override
        public void run() {
            int time = new Random().nextInt(2);
            System.out.println(Thread.currentThread().getName()
                + " process the task : " + taskName
                + "执行时间 ：" + time
            );
            SleepTools.second(time);
        }
    }

    public static class CusThreadPoolExecutor extends ThreadPoolExecutor {

        private static final String LOG_SPLIT = "-";

        public CusThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public CusThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public CusThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public CusThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }


        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println(t.getName() + LOG_SPLIT + ((Worker) r).getName() + "执行开始时间：" + DateFormatUtil.defaultFormat().format(new Date()));
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println(Thread.currentThread().getName() + LOG_SPLIT + ((Worker) r).getName() + "执行结束时间：" + DateFormatUtil.defaultFormat().format(new Date()));
        }
    }

    public static class DaemonThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "CUS-THREAD-" + count.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }
}
