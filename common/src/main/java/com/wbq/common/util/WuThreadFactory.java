package com.wbq.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

/**
 * @author: biqin.wu
 * @Date: 2019/1/19
 * @Time: 21:12
 * @Description:
 */
public class WuThreadFactory implements ThreadFactory {
    private final ThreadFactoryBuilder builder;

    private final String prefix;

    private ThreadFactory threadFactory;

    public WuThreadFactory(String prefix, boolean daemon) {
        this.builder = new ThreadFactoryBuilder();
        this.builder.setDaemon(daemon);
        this.builder.setNameFormat(prefix + "-thread-%d");
        this.prefix = prefix;
    }

    public WuThreadFactory(String prefix) {
        this(prefix, true);
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        if (threadFactory == null) {
            synchronized (this) {
                if (threadFactory == null) {
                    threadFactory = this.builder.build();
                }
            }
        }
        return this.threadFactory.newThread(r);
    }
}
