package com.com.jvm;

/**
 * 大对象直接进入老年代
 * -Xms20m -Xmx20m  -Xmn10m -XX:+PrintGCDetails
 * -XX:PretenureSizeThreshold=2m
 * -XX:+UseSerialGC
 */
public class BigAllocation {
    private static final int _1MB =1024*1024; //1M的大小
    // * 大对象直接进入老年代(Eden  2m  +1 )
    public static void main(String[] args) {
        byte[] b1,b2,b3,b4,b5,b6,b7,b8,
            b9,b10,b11,b12,b13,b14,b15,b16,b0;

        b1 = new byte[1*_1MB];
        b2=b1;b0= new byte[1*_1MB];
        b3=b1;b0= new byte[1*_1MB];
        b4=b1;b0= new byte[1*_1MB];
        b5=b1;b0= new byte[1*_1MB];
        b6=b1;b0= new byte[1*_1MB];
        b7=b1;b0= new byte[1*_1MB];
        b8=b1;b0= new byte[1*_1MB];
        b9=b1;b0= new byte[1*_1MB];
        b10=b1;b0= new byte[1*_1MB];
        b11=b1;b0= new byte[1*_1MB];
        b12=b1;b0= new byte[1*_1MB];
        b13=b1;b0= new byte[1*_1MB];
        b14=b1;b0= new byte[1*_1MB];
        b15=b1;b0= new byte[1*_1MB];
        b16=b1;b0= new byte[1*_1MB];





//
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代
//        b3 = new byte[5*_1MB];//这个对象直接进入老年代


    }

}
