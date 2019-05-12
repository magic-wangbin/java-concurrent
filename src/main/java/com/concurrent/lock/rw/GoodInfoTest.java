package com.concurrent.lock.rw;

import com.util.SleepTools;

import java.util.Random;

public class GoodInfoTest {
    //读写比例10:1
    private static final int READ_WRITE_RATIO = 10;
    //最少线程数
    private static final int MIN_THREAD_NUMBER = 3;

    /**
     * 读线程.
     */
    public static class ReadThread implements Runnable {

        private GoodInfoService goodInfoService;

        public ReadThread(GoodInfoService goodInfoService) {
            this.goodInfoService = goodInfoService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {//操作100次
                goodInfoService.getNum();
            }
            System.out.println(Thread.currentThread().getName() + "读取商品数据耗时："
                    + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * 写线程.
     */
    public static class WriteThread implements Runnable {

        private GoodInfoService goodInfoService;

        public WriteThread(GoodInfoService goodInfoService) {
            this.goodInfoService = goodInfoService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random r = new Random();
            for(int i=0;i<10;i++){//操作10次
                SleepTools.ms(50);
                goodInfoService.setNum(r.nextInt(10));
            }
            System.out.println(Thread.currentThread().getName()
                    +"写商品数据耗时："+(System.currentTimeMillis()-start)+"ms---------");
        }
    }

    public static void main(String[] args) {
        GoodInfo goodInfo = new GoodInfo("CUP", 1000, 200);
        //使用RW LOCK进行测试
        //GoodInfoService goodInfoService = new UseRWLock(goodInfo);
        GoodInfoService goodInfoService = new UseSychronized(goodInfo);

        for (int i = 0; i < MIN_THREAD_NUMBER; i++) {
            Thread setT = new Thread(new WriteThread(goodInfoService));
            for (int j = 0; j < READ_WRITE_RATIO; j++) {
                Thread getT = new Thread(new ReadThread(goodInfoService));
                getT.start();
            }
            SleepTools.ms(100);
            setT.start();
        }
    }
}
