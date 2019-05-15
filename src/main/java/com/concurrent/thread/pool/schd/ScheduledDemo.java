package com.concurrent.thread.pool.schd;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledDemo {

    private static final int CORE_POOL_SIZE = 1;


    //
    public static void main(String[] args) {
//
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);

        // 任务固定推迟[每个任务之间固定]
//        scheduleWithFixedDelayTest(executor);

        //固定时间间隔执行的任务,从理论上说第二次任务在6000 ms后执行，第三次在6000*2 ms后执行
//        scheduleAtFixedRateTest(executor);

        // 定时任务异常
//        executor.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.PROCESS_EXCEPTION),
//            0, 3000, TimeUnit.MILLISECONDS);

        // 固定时间间隔执行的任务,虽然抛出了异常,但被捕捉了,next周期继续运行
        executor.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HAS_EXCEPTION),
            0, 3000, TimeUnit.MILLISECONDS);

    }

    private static void scheduleWithFixedDelayTest(ScheduledThreadPoolExecutor executor) {
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("the task run delay 5s!");
            }
        }, 3, 5, TimeUnit.SECONDS);
    }

    private static void scheduleAtFixedRateTest(ScheduledThreadPoolExecutor executor) {
        executor.scheduleAtFixedRate(new ScheduleWorkerTime(), 0, 6, TimeUnit.SECONDS);
    }
}
