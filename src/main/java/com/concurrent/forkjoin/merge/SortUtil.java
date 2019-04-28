package com.concurrent.forkjoin.merge;

import com.util.SleepTools;

public class SortUtil {

    /**
     * 插入排序.
     */
    public static int[] insertSot(int[] intArray) {
        SleepTools.ms(1);
        if (intArray.length == 0)
            return intArray;
        //循环数组，对每个数字进行排序
        int currentValue;//当前需要比较的数字
        for (int i = 0; i < intArray.length - 1; i++) {
            //当前需要数字前一个索引位置
            int preIndex = i;
            //当前数字
            currentValue = intArray[preIndex + 1];
            //将currentValue插入preIndex之前某个位置
            while (preIndex >= 0 && currentValue < intArray[preIndex]) {
                intArray[preIndex + 1] = intArray[preIndex];
                preIndex--;
            }
            //当前值不比前边一个小，就在当前位置
            intArray[preIndex + 1] = currentValue;
        }
        return intArray;
    }

    /**
     * 最小任务的合并[两个已经排序好的数组的合并].
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left, int[] right) {
        SleepTools.ms(1);
        int[] result = new int[left.length + right.length];
        //i代表左边数组的下表，j代表右边数组的下表
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            //从左往右分别取数进行比较，如果左边的数小，左边坐标又移【继续取数】;右边同理
            //左侧取值完毕，右侧直接添加到左侧;右边同理
            if (i >= left.length) {//等于->左侧取值完毕
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] > right[j]) {
                result[index] = right[j++];
            } else {
                result[index] = left[i++];
            }
        }
        return result;
    }
}
