package com.concurrent.thread.pool;

import com.util.SleepTools;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 使用线程池.
 */
public class UseThreadPool {
    /*没有返回值*/
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
            System.out.println(Thread.currentThread().getName()
                + " process the task : " + taskName);
            SleepTools.ms(r.nextInt(100) * 5);
        }
    }

    /*有返回值*/
    public static class CallWorker implements Callable<String> {

        private String taskName;
        private Random r = new Random();

        public CallWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }

        @Override
        public String call() throws Exception {
            SleepTools.second(1);
            System.out.println(Thread.currentThread().getName()
                + " process the task : " + taskName);
            return Thread.currentThread().getName() + ":" + r.nextInt(100) * 5;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int coreNum = Runtime.getRuntime().availableProcessors();
        System.out.println("核心数为" + coreNum);
        System.out.println("=================");
        // 无返回值的任务
        ExecutorService executorService = new ThreadPoolExecutor(coreNum,
            coreNum,
            5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(10)
        );
//        for (int i = 0; i < 10; i++) {
//            executorService.execute(new Worker("[" + i + "]"));
//        }

        // 有返回值的任务
//        for (int i = 0; i <= 6; i++) {
//            CallWorker callWorker = new CallWorker("worker " + i);
//            System.out.println("A new task has been added : " + callWorker.getName());
//            Future<String> result = executorService.submit(callWorker);
//            System.out.println(result.get());
//        }
//        executorService.shutdown();
//        executorService.shutdownNow();

        //===============================================================//
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        ExecutorService threadPool1 = Executors.newSingleThreadExecutor();
        ExecutorService threadPool2 = Executors.newCachedThreadPool();
        ExecutorService threadPool3 = Executors.newWorkStealingPool();

        ExecutorService threadPool4 = Executors.newScheduledThreadPool(1);
        ExecutorService threadPool5 = Executors.newSingleThreadScheduledExecutor();

        // 有返回值的任务
        for (int i = 0; i <= 6; i++) {
            CallWorker callWorker = new CallWorker("worker " + i);
            System.out.println("A new task has been added : " + callWorker.getName());
            Future<String> result = threadPool2.submit(callWorker);
           // System.out.println(result.get());
        }
        threadPool2.shutdown();
        threadPool2.shutdownNow();

    }
}
