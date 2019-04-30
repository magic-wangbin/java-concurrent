package com.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class CusAtomicincreamentTest {
    AtomicInteger atomicInteger = new AtomicInteger();

    /*请完成这个递增方法*/
    public void increament() {
        //atomicInteger.getAndIncrement();
        for (; ; ) {
            int old = getCount();
            boolean flag = compareAndSet(old, ++old);
            if (flag) {
                break;
            }
        }
    }

    public int getCount() {
        return atomicInteger.get();
    }

    public boolean compareAndSet(int oldValue, int newValue) {
        return atomicInteger.compareAndSet(oldValue, newValue);
    }

    public static void main(String[] args) throws InterruptedException {
        CusAtomicincreamentTest halfAtomicInt = new CusAtomicincreamentTest();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        halfAtomicInt.increament();
                    }
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(halfAtomicInt.getCount());
    }
}
