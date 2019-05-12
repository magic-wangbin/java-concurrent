package com.concurrent.lock.condition;

import com.util.SleepTools;

public class ExpressCondTest {

//    private static ExpressCond expressCond = new ExpressCond(0, ExpressCond.CITY);

//    private static ExpressCondAll expressCond = new ExpressCondAll(0, ExpressCond.CITY);

    private static ExpressCondOneLock expressCond = new ExpressCondOneLock(0, ExpressCond.CITY);

    public static class KmWaitThread implements Runnable {
        @Override
        public void run() {
            expressCond.waitKm();
        }
    }

    public static class SiteWaitThread implements Runnable {
        @Override
        public void run() {
            expressCond.waitSite();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new KmWaitThread()).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new SiteWaitThread()).start();
        }

        SleepTools.ms(500);

        //开始唤起
        expressCond.changeKm();
        expressCond.changeKm();
        expressCond.changeKm();

        SleepTools.ms(1000);
        System.out.println("===============");
        expressCond.changeSite();
        expressCond.changeSite();
        expressCond.changeSite();
    }
}
