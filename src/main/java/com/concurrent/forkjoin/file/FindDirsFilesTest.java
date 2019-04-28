package com.concurrent.forkjoin.file;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class FindDirsFilesTest {
    public static void main(String[] args) {
        try {
            // 用一个 ForkJoinPool 实例调度总任务
            ForkJoinPool pool = new ForkJoinPool();
            FindFileAction task = new FindFileAction(new File("D:\\"));

            /*异步提交*/
            pool.execute(task);

            /*主线程做自己的业务工作*/
            System.out.println(Thread.currentThread().getName() + " is Running......");
            int otherWork = 0;
            for (int i = 0; i < 100; i++) {
                otherWork = otherWork + i;
            }
            System.out.println(Thread.currentThread().getName() + ",otherWork=" + otherWork);

            //此方法为阻塞方法等待fork_join执行完毕才进行主线程的继续执行
            task.join();
            System.out.println(Thread.currentThread().getName() + "主线程执行完毕");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
