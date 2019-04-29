package com.concurrent.future;

import com.util.SleepTools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {
    public static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            //
            LocalDateTime startTime = LocalDateTime.now();
            System.out.println(Thread.currentThread().getName() + "开始执行：" + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            //让此任务执行5秒
            int sum = 0;

            int currentSecond = 0;
            int oldSecond = 0;
            while (startTime.plusSeconds(5).compareTo(LocalDateTime.now()) > 0) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Callable子线程计算任务中断！");
                    return null;
                }
                sum++;
                currentSecond = LocalDateTime.now().getSecond();
                if (oldSecond == 0) {
                    oldSecond = currentSecond;
                    System.out.println("计时器：" + currentSecond);
                } else if (currentSecond > oldSecond) {
                    oldSecond++;
                    System.out.println("计时器：" + currentSecond);
                }
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " begin....");

        //
        FutureTask<Integer> future = new FutureTask<>(new CallableTask());

        new Thread(future).start();

        SleepTools.second(1);
        Random random = new Random();
        int i = random.nextInt(100);
        if (i > 50) {
            try {
                System.out.println("执行获取返回结果。。。。");
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("执行取消操作。。。。");
            future.cancel(true);
        }

        System.out.println(Thread.currentThread().getName() + " end....");
    }
}
