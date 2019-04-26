package com.concurrent.forkjoin.sum;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class SumTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] src = MakeArray.makeArray();
        //创建forkjoin池
        ForkJoinPool pool = new ForkJoinPool();
        //创建一个任务
        SumTask sumTask = new SumTask(src, 0, src.length - 1);

        long start = System.currentTimeMillis();
        //往pool添加任务
        //Integer sum = pool.invoke(sumTask);
        pool.execute(sumTask);
        System.out.println("The count is " + sumTask.join()
            + " spend time:" + (System.currentTimeMillis() - start) + "ms");
    }
}
