package com.simba.common;

/*
@Date 2022/5/29 18:28
@PackageName com.simba.common
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import java.util.concurrent.*;

public class ThreadPoolConfig {

    public static ExecutorService threadPoolExecutorGenerate = new ThreadPoolExecutor(
            ThreadPoolConstant.CORE_THREAD_NUM,
            ThreadPoolConstant.MAX_THREAD_NUM,
            ThreadPoolConstant.KEEP_ALIVE_TIME_SECONDS,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(ThreadPoolConstant.QUEUE_LENGTH),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
}
