package com.simba.common;

/*
@Date 2022/5/29 18:30
@PackageName com.simba.common
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.sun.javafx.binding.StringConstant;

public class ThreadPoolConstant {


    /**
     * 核心线程数量
     */
    public static final int CORE_THREAD_NUM = 10;

    /**
     * 最大线程数量
     */
    public static final int MAX_THREAD_NUM = 15;

    /**
     * 非核心线程存活时间
     */
    public static final long KEEP_ALIVE_TIME_SECONDS = 1L;

    /**
     * 任务队列长度
     */
    public static final int QUEUE_LENGTH = 8;

    /**
     * 线程超时时间
     */
    public static final long TIME_OUT = 70;

    private ThreadPoolConstant() {
        throw new AssertionError(ThreadPoolConstant.class.getName());
    }
}
