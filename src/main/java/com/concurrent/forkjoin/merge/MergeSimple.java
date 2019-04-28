package com.concurrent.forkjoin.merge;

import com.concurrent.forkjoin.sum.MakeArray;

import java.util.Arrays;

public class MergeSimple {


    /**
     * 归并排序.
     */
    public static int[] mergeSort(int[] array) {
        //可作为最小任务
        if (array.length < MakeArray.THRESHOLD) {
            return SortUtil.insertSot(array);
        }
        //
        int middle = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, middle);
        int[] right = Arrays.copyOfRange(array, middle, array.length);
        return SortUtil.merge(mergeSort(left), mergeSort(right));
    }

}
