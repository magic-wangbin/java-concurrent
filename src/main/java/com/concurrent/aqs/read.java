package com.concurrent.aqs;

/**
 * doc类.
 */
public class read {

    // LockSupport
    /**
     * LockSupport定义了一组的公共静态方法，这些方法提供了最基本的线程阻塞和唤醒功能，而LockSupport也成为构建同步组件的基础工具。
     * LockSupport定义了一组以park开头的方法用来阻塞当前线程，以及unpark(Thread thread)方法来唤醒一个被阻塞的线程。
     * LockSupport增加了park(Object blocker)、
     * parkNanos(Object blocker,long nanos)
     * 和parkUntil(Object blocker,long deadline)3个方法，
     * 用于实现阻塞当前线程的功能，其中参数blocker是用来标识当前线程在等待的对象（以下称为阻塞对象），该对象主要用于问题排查和系统监控。
     */



}
