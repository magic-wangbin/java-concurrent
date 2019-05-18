package com.concurrent.delay.queue;

import com.util.SleepTools;

import java.util.concurrent.DelayQueue;

public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<ItemVo<Order>> queue = new DelayQueue<ItemVo<Order>>();//延时队列

        new Thread(new PutOrder(queue)).start();
       // SleepTools.ms(3);
      //  new Thread(new FetchOrder(queue)).start();

        SleepTools.ms(5);

        Object[] object = queue.toArray();
        for (int i = 0; i < object.length; i++) {
            System.out.println(((ItemVo<Order>) object[i]).getData().getOrderNo());
        }

        //每隔500毫秒，打印个数字
        for (int i = 1; i < 15; i++) {
            Thread.sleep(500);
            System.out.println(i * 500);
        }
    }
}
