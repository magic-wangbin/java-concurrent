package com.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.get());
        // expect:当时获取到的值，update:cas操作进行修改
        System.out.println(atomicInteger.compareAndSet(2, 30));
        System.out.println(atomicInteger.addAndGet(500));

    }
}
