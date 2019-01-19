package com.wbq.common.util;

import java.util.concurrent.*;

/**
 * @author: biqin.wu
 * @Date: 2019/1/19
 * @Time: 21:06
 * @Description:
 */
public final class ThreadPoolUtil {

    private static final int corePoolSize = 20;

    private static final int maxPoolSize = 20;

    private static final int queueSize = 10000;

    private static final int keepAliveTime = 10;

    private static final BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(queueSize);

    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue, new WuThreadFactory("wu"), new RejectedHandler(new ThreadPoolExecutor.AbortPolicy()));

    private ThreadPoolUtil() {
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    private static class RejectedHandler implements RejectedExecutionHandler {
        private RejectedExecutionHandler rejectedExecutionHandler;

        RejectedHandler(RejectedExecutionHandler rejectedExecutionHandler) {
            this.rejectedExecutionHandler = rejectedExecutionHandler;
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            rejectedExecutionHandler.rejectedExecution(r, executor);
        }
    }

}
