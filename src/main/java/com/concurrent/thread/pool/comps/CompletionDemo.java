package com.concurrent.thread.pool.comps;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletionDemo {

    private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int TOTAL_TASK = POOL_SIZE * 10;

    public static void main(String[] args) throws Exception {

        testCompletion();
        cusCompletionTest();


    }

    public static void cusCompletionTest() throws InterruptedException, ExecutionException {

        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        //队列,拿任务的执行结果
        BlockingQueue<Future<Integer>> queue =
            new LinkedBlockingQueue<>();

        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<Integer> future = pool.submit(new WorkTask("ExecTask" + i));
            queue.add(future);
        }

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            int sleptTime = queue.take().get();
            //System.out.println(" slept "+sleptTime+" ms ...");
            count.addAndGet(sleptTime);
        }

        // 关闭线程池
        pool.shutdown();
        long end = (System.currentTimeMillis() - start);
        System.out.println("-------------tasks sleep time " + count.get()
            + "ms,and spend time "
            + end + " ms"
            + ",rate:" + BigDecimal.valueOf(count.get()).divide(BigDecimal.valueOf(end), 2, RoundingMode.HALF_UP));
    }

    /**
     * 使用completion进行计时统计.
     */
    public static void testCompletion() throws Exception {
        long start = System.currentTimeMillis();

        AtomicInteger count = new AtomicInteger(0);
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        CompletionService<Integer> completionService = new ExecutorCompletionService<>(pool);

        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            completionService.submit(new WorkTask("ExecTask" + i));
        }

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            int sleptTime = completionService.take().get();
            //System.out.println(" slept "+sleptTime+" ms ...");
            count.addAndGet(sleptTime);
        }

        // 关闭线程池
        pool.shutdown();
        long end = (System.currentTimeMillis() - start);
        System.out.println("-------------tasks sleep time " + count.get()
            + "ms,and spend time "
            + end + " ms"
            + ",rate:" + BigDecimal.valueOf(count.get()).divide(BigDecimal.valueOf(end), 2, RoundingMode.HALF_UP));

    }

}
