package com.concurrent.delay.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 类说明：存放的队列的元素，
 */
public class ItemVo<T> implements Delayed {

    //到期时间,但传入的数值代表过期的时长，传入单位秒
    private long activeTime;
    private T data;//业务数据，泛型

    public ItemVo(long expirationTime, T data) {
        // 实际到期的时间
        this.activeTime = expirationTime * 1000 + System.currentTimeMillis();
        this.data = data;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getData() {
        return data;
    }

    /*
     * 这个方法返回到激活日期的剩余时间，时间单位由单位参数指定。
     */
    @Override
    public long getDelay(TimeUnit unit) {
        // 剩余时间
        long d = unit.convert((this.activeTime - System.currentTimeMillis()), unit);
        return d;
    }

    /*
     *Delayed接口继承了Comparable接口，按剩余时间排序，实际计算考虑精度为纳秒数
     */
    @Override
    public int compareTo(Delayed o) {
        long stillTime = getDelay(TimeUnit.MILLISECONDS);
        long delayedTime = o.getDelay(TimeUnit.MILLISECONDS);
        long d = stillTime - delayedTime;
        if (d == 0) {
            return 0;
        } else {
            if (d < 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
