package com.com.jvm.type;

/**
 * 为什么需要泛型
 */
public class NeedGeneric {

//    public int addInt(int x,int y){
//        return x+y;
//    }
//
//    public float addFloat(float x,float y){
//        return x+y;
//    }

    //泛型方法
    public <T extends Number> Number add(T x, T y) {
        return x.doubleValue() + y.doubleValue();
    }

    public static void main(String[] args) {
        //不使用泛型
        NeedGeneric needGeneric = new NeedGeneric();
//        System.out.println(needGeneric.addInt(1,2));
//        System.out.println(needGeneric.addFloat(1.2f,2.4f));

        //使用泛型
        System.out.println(needGeneric.add(3.2d, 4.5d));
        System.out.println(needGeneric.add(1, 2));
    }

}
