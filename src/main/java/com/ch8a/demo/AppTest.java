package com.ch8a.demo;

import com.ch8a.PendingJobPool;
import com.ch8a.vo.TaskResult;
import com.util.SleepTools;

import java.util.List;
import java.util.Random;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * <p>
 * 类说明：模拟一个应用程序，提交工作和任务，并查询任务进度
 */
public class AppTest {

    private final static String JOB_NAME = "计算数值";
    private final static int JOB_LENGTH = 1000;

    //查询任务进度的线程
    private static class QueryResult implements Runnable {

        private PendingJobPool pool;

        public QueryResult(PendingJobPool pool) {
            super();
            this.pool = pool;
        }

        @Override
        public void run() {
            int i = 0;
            while (i < 950) {
                List<TaskResult<String>> taskDetail = pool.getTaskDetail(JOB_NAME);
                if (!taskDetail.isEmpty()) {
                    System.out.println(pool.getTaskProgess(JOB_NAME));
                    System.out.println(taskDetail);
                }
                SleepTools.ms(100);

                if (pool.getMap().get(JOB_NAME) == null) {
                    System.out.println("任务执行完毕！");
                    break;
                }
                i++;
            }
        }

    }

    public static void main(String[] args) {
        // 定义一个任务
        MyTask myTask = new MyTask();
        // 定义执行的线程池
        PendingJobPool pool = PendingJobPool.getInstance();
        // 往线程池中放入一个Job
        pool.registerJob(JOB_NAME, JOB_LENGTH, myTask, 5);

        Random r = new Random();

        // 往线程池中提交任务
        for (int i = 0; i < JOB_LENGTH; i++) {
            pool.putTask(JOB_NAME, r.nextInt(1000));
        }

        //
        Thread t = new Thread(new QueryResult(pool));
        t.start();
    }
}
