package com.concurrent.cus.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 使用信号量定义阻塞队列.
 *
 * @param <T>
 */
public class SemphoreImpl<T> implements IBoundedBuffer<T> {

    private final Semaphore items, spaces;
    private List queue = new LinkedList();

    public SemphoreImpl(int capacityInt) {
        this.spaces = new Semaphore(capacityInt);
        this.items = new Semaphore(0);
    }

    @Override
    public void put(T x) throws InterruptedException {
        //拿到一个许可证
        spaces.acquire();
        synchronized (this) {
            queue.add(x);
        }
        //放入一个许可证，可以使用
        items.release();
    }

    @Override
    public T take() throws InterruptedException {
        items.acquire();
        T t;
        synchronized (this) {
            t = (T) queue.remove(0);
        }
        spaces.release();
        return t;
    }
}
