package com.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * atomic array test
 */
public class AtomicArrayTest {
    public static void main(String[] args) {
        int[] intArray = {1, 2, 3, 4, 5};
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(intArray);
        atomicIntegerArray.getAndSet(2, 30);
        System.out.println(atomicIntegerArray.get(2));
        System.out.println(intArray[2]);

        System.out.println("===================================================");

    }
}
