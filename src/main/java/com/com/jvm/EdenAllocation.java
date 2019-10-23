package com.com.jvm;

/**
 * 对象优先在Eden分配
 * -Xms20m -Xmx20m  -Xmn10m -XX:+PrintGCDetails
 */
public class EdenAllocation {
    private static final int _1MB = 1024 * 1024; //1M的大小

    //

    // * 对象优先在Eden分配
    public static void main(String[] args) {
        byte[] b1, b2, b3, b4, b5, b6,b7,b8;
//        b1 = new byte[1 * _1MB];
//        b2 = new byte[1 * _1MB];
//        b3 = new byte[1 * _1MB];
//        b4 = new byte[1 * _1MB];
//        b6 = new byte[1 * _1MB];
//        b7 = new byte[1 * _1MB];
//        b8 = new byte[3 * _1MB];
//
//        // ----------------以上新生代内存占据76%~9216K ----------------
//        b5 = new byte[4 * _1MB];


    }

}
