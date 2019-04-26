package com.concurrent.forkjoin.sum;

import java.util.Random;

/**
 * 生成数组的类.
 */
public class MakeArray {
    //固定数组的长度
    public static final int ARRAY_LENGTH = 400;

    //生成数组的方法
    public static int[] makeArray() {

        //new一个随机数发生器
        Random r = new Random();
        int[] result = new int[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            //用随机数填充数组
            result[i] = 100;
        }
        return result;
    }
}
