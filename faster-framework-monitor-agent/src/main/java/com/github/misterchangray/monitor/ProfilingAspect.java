package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.stacktrace.StackTraceElementExt;
import com.github.misterchangray.monitor.utils.BannerUtils;
import com.github.misterchangray.monitor.utils.Logger;

/**
 * Created by LinShunkang on 2018/4/15
 */
public final class ProfilingAspect {
    static ILogger logger = LoggerFactory.getLogger("monitor-methods.log");

    private static final MethodTagMaintainer methodTagMaintainer = MethodTagMaintainer.getInstance();

    private ProfilingAspect() {
        //empty
    }

    static ThreadLocal<StackTraceElementExt> threadLocal = new ThreadLocal();
    private static long millis = 1000000;
    private static long sec = 1000 * millis;
    public static void profiling(final long startNanos, final int methodTagId) {
        try {
            long i = (System.nanoTime() - startNanos);
            if(i <= millis) return;

            long limit = sec * ProfilingConfig.getMonitorConfig().getMaxTTLOfSec();
            if(i < limit) {
                return;
            }

            long spend = i / millis;

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if(threadLocal.get() == null) {
                threadLocal.set(new StackTraceElementExt());
            }

            if(threadLocal.get().getStackTraceElements() == null || threadLocal.get().getStackTraceElements().length < stackTrace.length) {
                threadLocal.get().setStackTraceElements(stackTrace);
                threadLocal.get().setTimes(new long[stackTrace.length]);
            }

            if(threadLocal.get().getStackTraceElements().length > 0) {
                for (int i1 = 0; i1 < threadLocal.get().getStackTraceElements().length; i1++) {
                    StackTraceElement stackTraceElement = threadLocal.get().getStackTraceElements()[i1];
                    if(stackTraceElement.getMethodName().equals( methodTagMaintainer.getMethodTag(methodTagId).getMethodName())) {
                        threadLocal.get().getTimes()[i1] = spend;
                    }
                }
            }


            long now = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder();
            sb.append(BannerUtils.buildBanner("MonitorJ Method ", now - spend, now));

            if(Thread.currentThread().getStackTrace().length > 0 && threadLocal.get() != null) {
                for (int i1 = 1; i1 < threadLocal.get().getStackTraceElements().length; i1++) {
                    sb.append(threadLocal.get().getStackTraceElements()[i1].toString());
                    sb.append(" : ");
                    sb.append(threadLocal.get().getTimes()[i1]);
                    sb.append(Consts.LINE_SEPARATOR);
                }

                Recorders.record(new Recorder(logger, true, sb.toString()));
            }
        } catch (Exception e) {
            Logger.error("ProfilingAspect.profiling(" + startNanos + ", " + methodTagId + ", "
                    + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ")", e);
        }
    }

}
