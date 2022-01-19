package com.github.misterchangray.monitor.utils;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ray.chang on 2021/1/22
 */
public final class LightWeightScheduler {

    private static final ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(4,
            ThreadUtils.newThreadFactory("MonitorJ-LightWeightScheduler-"),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void exec(Runnable runnable) {
       exec(runnable, 0);
    }


    public static void exec(Runnable runnable, int ttl) {
        if(scheduledExecutor.getQueue().size() > 100) {
            return;
        }

        try {
            if(ttl == 0) {
                scheduledExecutor.execute(runnable);
            } else {
                scheduledExecutor.scheduleWithFixedDelay(runnable, 0, ttl, TimeUnit.SECONDS);
            }
        } catch (Exception ae) {}
    }

}
