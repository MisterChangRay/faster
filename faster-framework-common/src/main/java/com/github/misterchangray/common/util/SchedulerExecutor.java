package com.github.misterchangray.common.util;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ray.chang on 2021/1/22
 */
public final class SchedulerExecutor {

    private static final ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(4,
            ThreadUtils.newThreadFactory("faster-framework-scheduler-"),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void exec(Runnable runnable) {
       execOnce(runnable, 0);
    }

    public static boolean execOnce(Runnable runnable, int ttl) {
        if(scheduledExecutor.getQueue().size() > 1000) {
            return false;
        }

        scheduledExecutor.schedule(runnable, ttl, TimeUnit.SECONDS);
        return true;
    }

    public static boolean execWithFixedDelay(Runnable runnable, int ttl) {
        if (scheduledExecutor.getQueue().size() > 1000) {
            return false;
        }

        scheduledExecutor.scheduleWithFixedDelay(runnable, 0, ttl, TimeUnit.SECONDS);
        return true;
    }

    public static ScheduledThreadPoolExecutor getScheduledExecutor() {
        return scheduledExecutor;
    }
}
