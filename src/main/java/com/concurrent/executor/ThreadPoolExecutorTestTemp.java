package com.concurrent.executor;

import com.util.SleepTools;

import java.util.concurrent.*;

/**
 * 线程池测试类.
 */
public class ThreadPoolExecutorTestTemp {
    private static ThreadPoolExecutor threadPoolExecutor = null;

    public static void main(String[] args) {
        //核心池的大小,当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中
        int corePoolSize = 5;
        //线程池最大线程数，这个参数也是一个非常重要的参数，它表示在线程池中最多能创建多少个线程；
        int maximumPoolSize = 5;
        //表示线程没有任务执行时最多保持多久时间会终止。
        long keepAliveTime = 5;
        //参数keepAliveTime的时间单位
        TimeUnit timeUnit = TimeUnit.SECONDS;
        //任务队列(SynchronousQueue->不保存任务)
        BlockingQueue<Runnable> blockingDeque = new SynchronousQueue<Runnable>();

        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, blockingDeque);
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new ThreadWork());
        }
        //

        //
        threadPoolExecutor.shutdown();
        // shutdown状态下再添加一个
        threadPoolExecutor.execute(new ThreadWork());


        System.out.println("end......");
    }

    public static class ThreadWork implements Runnable {

        @Override
        public void run() {
            SleepTools.second(500000);
            System.out.println("执行结束");
        }
    }


}
