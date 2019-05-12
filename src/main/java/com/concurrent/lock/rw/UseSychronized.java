package com.concurrent.lock.rw;

import com.util.SleepTools;

/**
 * 使用内置锁实现商品业务安全.
 */
public class UseSychronized implements GoodInfoService {

    private GoodInfo goodInfo;

    public UseSychronized(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public synchronized GoodInfo getNum() {
        SleepTools.ms(5);
        return goodInfo;
    }

    @Override
    public synchronized void setNum(int number) {
        SleepTools.ms(5);
        goodInfo.changeNumber(number);
    }
}
