package com.concurrent.bitwise;

import java.io.UnsupportedEncodingException;

/*
 * 位运算
 * */
public class IntToBinary {

    public static void main(String[] args) throws UnsupportedEncodingException {

        //toBinaryString->十进制装换成2进制
        System.out.println("the 4 is : " + Integer.toBinaryString(4)); //100
        System.out.println("the 6 is : " + Integer.toBinaryString(6)); //110

        //位与&(真真为真 真假为假 假假为假)
        System.out.println("the 4&6 is : " + Integer.toBinaryString(6 & 4)); //100

        //位或|(真真为真 真假为真 假假为假)
        System.out.println("the 4|6 is : " + Integer.toBinaryString(6 | 4)); //110

        //位非~
        System.out.println("the ~4 is : " + Integer.toBinaryString(~4)); // 11111111111111111111111111111-011[“-”是故意分割的]

        //位异或^(真真为假 真假为真 假假为假)
        System.out.println("the 4^6 is : " + Integer.toBinaryString(6 ^ 4)); //010


        //有符号右移>>(若正数,高位补0,负数,高位补1) //100
        System.out.println("the 4>>1 is : " + Integer.toBinaryString(4 >> 2));//10 -->正数相当于除2
        System.out.println("the -4>>1 is : " + Integer.toBinaryString(-4 >> 1)); //11111111111111111111111111111110

        //有符号左移<<(若正数,高位补0,负数,高位补1)
        System.out.println("the 4<<1 is : " + Integer.toBinaryString(4 << 1));//1000->乘2
        System.out.println("the -4<<1 is : " + Integer.toBinaryString(-4 << 1)); //11111111111111111111111111111000

        System.out.println("========================================================================");
        //无符号右移>>>(不论正负,高位均补0)
        System.out.println("the 234567 is : " + Integer.toBinaryString(234567));//111001010001000111
        System.out.println("the 234567>>>4 is : " + Integer.toBinaryString(234567 >>> 1));//11100101000100

        //无符号右移>>>(不论正负,高位均补0)
        System.out.println("the -4 is : " + Integer.toBinaryString(-4));//11111111111111111111111111111100
        System.out.println("the -4>>>4 is : " + Integer.toBinaryString(-4 >>> 4));//1111111111111111111111111111
        System.out.println(Integer.parseInt(Integer.toBinaryString(-4 >>> 4), 2));//268435455


        //取模a % (2^n) 等价于 a & (2^n - 1)
        System.out.println("the 345 % 16 is : " + (345 % 16) + " == " + (345 & (16 - 1)));

        System.out.println("Mark hashCode is : " + "Mark".hashCode() + "="
            + Integer.toBinaryString("Mark".hashCode()));
        System.out.println("Bill hashCode is : " + "Bill".hashCode() + "="
            + Integer.toBinaryString("Bill".hashCode()));
    }
}
