package com.concurrent.notify.demo;

import com.util.SleepTools;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerInJava {
    public static AtomicInteger productNumber = new AtomicInteger();
    public static AtomicInteger got = new AtomicInteger();

    public static void main(String[] args) {
        LinkedList<Integer> buffer = new LinkedList<>();
        int maxSize = 10;

        new Producers(buffer, productNumber, "PRODUCER").start();
        for (int i = 0; i < 20; i++) {
            Thread consumer = new Consumers(buffer, got, "CONSUMER");
            consumer.start();
        }

        SleepTools.second(5);
        System.out.println("got:" + got);
        System.out.println("productNumber:" + productNumber);
    }
}

//生产者线程
class Producers extends Thread {
    private LinkedList<Integer> queue;
    private AtomicInteger productNumber;
    private String name;

    public Producers(LinkedList<Integer> queue, AtomicInteger productNumber, String name) {
        this.queue = queue;
        this.name = name;
        this.productNumber = productNumber;
    }

    @Override
    public void run() {
        synchronized (queue) {
            while (true) {
                while (queue.size() == 20) { //当缓存区满的时候
                    try {
                        //进入wait
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                //缓存区不为空的时候就可以继续生产，生产后唤醒消费者线程的wait
                queue.addLast(1);
                productNumber.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + name + "input ....");
                queue.notifyAll();
            }
        }
    }
}

class Consumers extends Thread {
    private LinkedList<Integer> queue;
    private AtomicInteger count;
    private String name;

    public Consumers(LinkedList<Integer> queue, AtomicInteger count, String name) {
        this.queue = queue;
        this.count = count;
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (queue) {
            while (queue.isEmpty()) { //当缓存区为空的时候

                try {
                    queue.wait(); //进入wait
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            queue.removeFirst();
            System.out.println(Thread.currentThread().getName() + name + "取出来一个");
            count.getAndIncrement();
            //当缓存区不为空的时候，就可以唤醒所有的wait的消费者线程或者生产者线程
            queue.notifyAll();
        }
    }
}

