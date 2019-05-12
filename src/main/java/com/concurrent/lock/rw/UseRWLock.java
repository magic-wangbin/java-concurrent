package com.concurrent.lock.rw;

import com.util.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseRWLock implements GoodInfoService {

    private GoodInfo goodInfo;

    public UseRWLock(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    //---------------RW LOCK--------------
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    //------------------------------------

    @Override
    public GoodInfo getNum() {
        readLock.lock();
        try {
            SleepTools.ms(5);
            return goodInfo;
        } finally {
            readLock.unlock();
        }


    }

    @Override
    public void setNum(int number) {
        writeLock.lock();
        try {
            goodInfo.changeNumber(number);
        } finally {
            writeLock.unlock();
        }
    }
}
