package com.concurrent.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 类说明：演示带版本戳的原子操作类
 */
public class UseAtomicStampedReference {
    //定义一个string类型的atomic ref stamped
    public static AtomicStampedReference<String> atomicStampedReference =
        new AtomicStampedReference<>("处理中", 0);

    //最初的版本
    final static int version = atomicStampedReference.getStamp();

    public static void main(String[] args) {


        // 修改atomicStampedReference中的状态值
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process(atomicStampedReference.getReference(), "成功", version, version + 1);
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process(atomicStampedReference.getReference(), "失败", version, version + 1);
            }
        });


        try {
            thread1.start();
            thread1.join();
            thread2.start();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void process(String oldValue, String value, int oldVersion, int version) {
        boolean flag = atomicStampedReference.compareAndSet(oldValue, value, oldVersion, version);
        System.out.println("[OLD_VALUE]=" + oldValue
            + ",[NEW VALUE]=" + value
            + ",[OLD_VERSION]=" + oldValue
            + ",[NEW VERSION]=" + version);
        System.out.println(Thread.currentThread().getName() + "：执行的结果->" + flag);
    }
}
