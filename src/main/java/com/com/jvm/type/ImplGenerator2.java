package com.com.jvm.type;

/**
 *  实现泛型类，方式2
 */
public class ImplGenerator2 implements Generator<String> {
    @Override
    public String next() {
        return "King";
    }

    public static void main(String[] args) {
        ImplGenerator2 implGenerator2 = new ImplGenerator2();
        System.out.println(implGenerator2.next());
    }
}
