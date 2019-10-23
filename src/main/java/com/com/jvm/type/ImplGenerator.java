package com.com.jvm.type;

/**
 * 实现泛型类，方式1
 * 引入一个类型变量T（其他大写字母都可以，不过常用的就是T，E，K，V等等）
 */
public class ImplGenerator<T> implements Generator<T> {

    private T data;

    public ImplGenerator(T data) {
        this.data = data;
    }

    @Override
    public T next() {
        return data;
    }

    public static void main(String[] args) {
        Generator<String> implGenerator = new ImplGenerator<>("King");
        System.out.println(implGenerator.next());

    }
}
