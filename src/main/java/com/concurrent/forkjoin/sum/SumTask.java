package com.concurrent.forkjoin.sum;

import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {

    //阈值,任务长度大于阈值就要继续分割
    private final static int THRESHOLD = MakeArray.ARRAY_LENGTH / 11;

    // 要处理的数组
    private int[] src;

    //任务的起始位置
    private int fromIndex, endIndex;

    public SumTask(int[] src, int fromIndex, int endIndex) {
        this.src = src;
        this.fromIndex = fromIndex;
        this.endIndex = endIndex;
    }

    /**
     * 任务处理的实际方法.
     *
     * @return
     */
    @Override
    protected Integer compute() {
        //
        if (endIndex - fromIndex < THRESHOLD) {
            int sum = 0;
            for (int i = fromIndex; i <= endIndex; i++) {
                sum = sum + src[i];
            }
            System.out.println(" from index = " + fromIndex + " endIndex=" + endIndex + ",sum=" + sum);
            return sum;
        }
        //需要继续拆分
        int mid = (fromIndex + endIndex) / 2;
        //创建新的任务
        SumTask leftSumTask = new SumTask(src, fromIndex, mid);
        SumTask rightSumTask = new SumTask(src, mid + 1, endIndex);
        //
        leftSumTask.invoke();

        //
        invokeAll(leftSumTask, rightSumTask);

        return leftSumTask.join() + rightSumTask.join();
    }
}
