package com.concurrent.executor;

import com.util.SleepTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutor2 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("使用线程池！");
            }
        });

        SleepTools.second(1);
    }
}
