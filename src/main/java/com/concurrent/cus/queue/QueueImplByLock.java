package com.concurrent.cus.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用锁实现阻塞队列.
 *
 * @param <T>
 */
public class QueueImplByLock<T> implements IBoundedBuffer<T> {

    /**
     * 说明：
     * 1：定义个容器存储队列内容--list/object[]
     * 2: 当put满了就等待，不满的时候，每添加一个就进行唤醒 -- lock condition
     * 3：take 空了就等待，take拿到一个对象的时候通知可以进行添加
     **/

    private static final int DEFAULT_NUM = 20;

    private int size;

    private final List<T> queue = new LinkedList<>();

    private Lock lock = new ReentrantLock();
    private Condition takeCondition = lock.newCondition();
    private Condition putConditon = lock.newCondition();

    public QueueImplByLock() {
        this(DEFAULT_NUM);
    }

    public QueueImplByLock(int size) {
        if (size <= 0) {
            this.size = DEFAULT_NUM;
        } else {
            this.size = size;
        }

    }

    @Override
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() >= size) {
                putConditon.await();
            }
            //
            queue.add(x);
            takeCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() <= 0) {
                takeCondition.await();
            }
            //
            T t = queue.remove(0);
            putConditon.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }
}
