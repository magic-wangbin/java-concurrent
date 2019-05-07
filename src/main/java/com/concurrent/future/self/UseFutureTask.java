package com.concurrent.future.self;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;


/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * <p>
 * 类说明：演示Future等的使用
 */
public class UseFutureTask {


    /*实现Callable接口，允许有返回值*/
    private static class UseCallable implements Callable<Integer> {
        private int sum;

        @Override
        public Integer call() throws Exception {
            System.out.println("Callable子线程开始计算！");
            Thread.sleep(5000);
            for (int i = 0; i < 5000; i++) {
                sum = sum + i;
            }
            System.out.println("Callable子线程计算结束！结果为: " + sum);
            return sum;
        }
    }

    public static void main(String[] args)
        throws InterruptedException, ExecutionException {

        //用FutureTask包装Callable
        final CustomFutureTask<Integer> futureTask = new CustomFutureTask<>(new UseCallable());
        new Thread(futureTask).start();//交给Thread去运行
        //Thread.sleep(2000);
        System.out.println("Main get UseCallable result = " + futureTask.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Sub get UseCallable result = "
                        + futureTask.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
