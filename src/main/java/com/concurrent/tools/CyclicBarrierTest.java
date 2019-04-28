package com.concurrent.tools;

import com.util.SleepTools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * 类说明：演示CyclicBarrier用法,共4个子线程，他们全部完成工作后，交出自己结果，
 * 再被统一释放去做自己的事情，而交出的结果被另外的线程拿来拼接字符串
 */
public class CyclicBarrierTest {

    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new CollectThread());

    //存放子线程工作结果的容器
    private static ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();

    /**
     * 汇总线程.
     */
    public static class CollectThread extends Thread {
        @Override
        public void run() {
            System.out.println("=================汇总部分====================");
            System.out.println("collectionThread begin start .... ....");
            //获取每个线程的执行结果，进行相应的汇总

            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, Long> workResult : resultMap.entrySet()) {
                result.append("[" + workResult.getValue() + "]");
            }
            System.out.println(" the result = " + result);
            System.out.println("do other business........");

            System.out.println("collectionThread begin end .... ....");
        }
    }

    /**
     * 子线程.
     */
    public static class SubThread extends Thread {
        @Override
        public void run() {
            System.out.println("subthread begin .....");
            Random random = new Random();
            SleepTools.second(random.nextInt(10));
            try {
                Long threadId = Thread.currentThread().getId();
                resultMap.put(threadId.toString(), threadId);

                System.out.println("subthread 开始等待其他线程");
                cyclicBarrier.await();

                //第一次等待完成后，再一次相互等待
                Thread.sleep(1000 + threadId);
                System.out.println("Thread_" + threadId + " ....do its business ");
                cyclicBarrier.await();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("main thread begin ....");

        for (int i = 0; i < 4; i++) {
            new SubThread().start();
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        for (int j = 0; j < 3; j++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("aaaaaaaaaaaaaaa");
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }
        try {
            cyclicBarrier.await();
            System.out.println("四个线程已经执行完毕！main thread begin again");
            SleepTools.second(1);
            System.out.println("睡眠一秒结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
