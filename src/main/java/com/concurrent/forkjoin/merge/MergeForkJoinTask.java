package com.concurrent.forkjoin.merge;

import com.concurrent.forkjoin.sum.MakeArray;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeForkJoinTask extends RecursiveTask<int[]> {

    public int[] src;

    public MergeForkJoinTask(int[] src) {
        this.src = src;
    }

    /**
     * 计算.
     *
     * @return
     */
    @Override
    protected int[] compute() {
        //确定任务是否继续划分
        if (src.length < MakeArray.THRESHOLD) {
            return SortUtil.insertSot(src);
        } else {
            int mid = src.length / 2;
            //任务进行拆分
            MergeForkJoinTask left = new MergeForkJoinTask(Arrays.copyOfRange(src, 0, mid));
            MergeForkJoinTask right = new MergeForkJoinTask(Arrays.copyOfRange(src, mid, src.length));
            invokeAll(left, right);
            return SortUtil.merge(left.join(), right.join());
        }
    }
}
