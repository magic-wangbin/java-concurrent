package com.concurrent.forkjoin.file;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class FindDirsFilesTest {
    public static void main(String[] args) {
        try {
            // 用一个 ForkJoinPool 实例调度总任务
            ForkJoinPool pool = new ForkJoinPool();
            FindFileAction task = new FindFileAction(new File("E:\\"));

            /*异步提交*/
            pool.execute(task);

            task.join();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
