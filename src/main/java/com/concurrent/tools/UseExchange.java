package com.concurrent.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * 类说明：演示Exchange用法
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchange = new Exchanger<Set<String>>();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setA = new HashSet<String>();//存放数据的容器
                try {
                    /*添加数据
                     * set.add(.....)
                     * */
                    setA.add(Thread.currentThread().getName());
                    setA = exchange.exchange(setA);//交换set
                    /*处理交换后的数据*/
                    System.out.println(Thread.currentThread().getName() + "->" + setA.iterator().next());
                } catch (InterruptedException e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setB = new HashSet<String>();//存放数据的容器
                try {
                    /*添加数据
                     * set.add(.....)
                     * set.add(.....)
                     * */
                    setB.add(Thread.currentThread().getName());
                    setB = exchange.exchange(setB);//交换set
                    /*处理交换后的数据*/
                    System.out.println(Thread.currentThread().getName() + "->" + setB.iterator().next());
                } catch (InterruptedException e) {
                }
            }
        }).start();



        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setC = new HashSet<String>();//存放数据的容器
                try {
                    /*添加数据
                     * set.add(.....)
                     * set.add(.....)
                     * */
                    setC.add("XIAN_CHENG_01");
                    setC = exchange.exchange(setC);//交换set
                    /*处理交换后的数据*/
                    System.out.println("XIAN_CHENG_01" + "->" + setC.iterator().next());
                } catch (InterruptedException e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setD = new HashSet<String>();//存放数据的容器
                try {
                    /*添加数据
                     * set.add(.....)
                     * set.add(.....)
                     * */
                    setD.add("XIAN_CHENG_02");
                    setD = exchange.exchange(setD);//交换set
                    /*处理交换后的数据*/
                    System.out.println("XIAN_CHENG_02" + "->" + setD.iterator().next());
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }
}
