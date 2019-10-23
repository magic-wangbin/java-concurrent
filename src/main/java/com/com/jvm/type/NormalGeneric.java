package com.com.jvm.type;

/**
 * 泛型类
 * 引入一个类型变量T（其他大写字母都可以，不过常用的就是T，E，K，V等等）
 */
public class NormalGeneric<T> {
    private T data;

    public NormalGeneric() {
    }

    public NormalGeneric(T data) {
        this();
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        NormalGeneric<String> normalGeneric = new NormalGeneric<>();
        normalGeneric.setData("King");
        System.out.println(normalGeneric.getData());
    }
}
