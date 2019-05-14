package com.concurrent.cus.queue;

/**
 * 自定义阻塞队列.
 *
 * @param <T>
 */
public interface IBoundedBuffer<T> {

    void put(T x) throws InterruptedException;

    T take() throws InterruptedException;

}
