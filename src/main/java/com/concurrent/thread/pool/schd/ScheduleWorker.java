package com.concurrent.thread.pool.schd;

import com.util.DateFormatUtil;

import java.util.Date;

/**
 * 类说明：定时任务的工作类
 */
public class ScheduleWorker implements Runnable {
    public final static int NORMAL = 0;//普通任务类型
    public final static int HAS_EXCEPTION = -1;//会抛出异常的任务类型
    public final static int PROCESS_EXCEPTION = 1;//抛出异常但会捕捉的任务类型

    //public static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int taskType;

    public ScheduleWorker(int taskType) {
        this.taskType = taskType;
    }

    @Override
    public void run() {
        if (taskType == HAS_EXCEPTION) {
            System.out.println(DateFormatUtil.defaultFormat().format(new Date())
                + " Exception be made,will next task run?");
            throw new RuntimeException("ExceptionHappen");
        } else if (taskType == PROCESS_EXCEPTION) {
            try {
                System.out.println("PROCESS_EXCEPTION ..."
                    + DateFormatUtil.defaultFormat().format(new Date()));
                throw new RuntimeException("PROCESS_EXCEPTION");
            } catch (RuntimeException e) {
                System.out.println("ProcessException catched,,will next task run?");
            }
        } else {
            System.out.println("Normal..." + DateFormatUtil.defaultFormat().format(new Date()));
        }
    }
}
