package com.concurrent.forkjoin.merge;

import com.concurrent.forkjoin.sum.MakeArray;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class MergeTest {
    /////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {

        int[] src = MakeArray.makeArray();
        int[] src2 = Arrays.copyOfRange(src, 0, src.length);

        //单线程进行排序
        System.out.println("单线程进行排序start........");
        Long startTime = System.currentTimeMillis();
        int[] array1 = MergeSimple.mergeSort(src);
        System.out.println("单线程进行排序耗时：" + (System.currentTimeMillis() - startTime));
        for (int a : array1) {
            System.out.println(a);
        }

        //单线程进行排序
        System.out.println("fork_join 进行排序start........");
        Long startTime2 = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<int[]> task = new MergeForkJoinTask(src2);
        pool.execute(task);
        int[] array2 = task.join();
        System.out.println("fork_join 进行排序耗时：" + (System.currentTimeMillis() - startTime2));
        for (int b : array2) {
            System.out.println(b);
        }
    }
}
