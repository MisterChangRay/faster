package com.github.misterchangray.monitor;

import com.github.misterchangray.common.consts.SystemConst;
import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.MethodConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.stacktrace.StackTraceElementExt;
import com.github.misterchangray.monitor.utils.BannerUtils;
import com.github.misterchangray.monitor.utils.Logger;

/**
 * Created by ray.chang on 2022/1/19
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

            MethodTag methodTag = methodTagMaintainer.getMethodTag(methodTagId);

            long limit = sec * ProfilingConfig.getCustomConfig().getMaxTTLOfSec();
            MethodConfig methodConfig = ProfilingConfig.getCustomConfig().getMethodsConfig().get(methodTag.getSimpleMethodDesc());
            if(null != methodConfig) {
                limit = sec * methodConfig.getTtlOfSec();
            }

            if(i < limit) {
                return;
            }

            long spend = i / millis;
            if(threadLocal.get() == null) {
                threadLocal.set(new StackTraceElementExt());
            }

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            // 1. getstacktrace
            // 2. profilling
            // 3. targetmthods
            StackTraceElement stackTraceElement = stackTrace[2];
            if(stackTraceElement.getMethodName().equals(methodTag.getMethodName())) {
                threadLocal.get().getSb().append(stackTraceElement.toString());
                threadLocal.get().getSb().append(" : ");
                threadLocal.get().getSb().append(spend);
                threadLocal.get().getSb().append(SystemConst.LINE_SEPARATOR);
            }

            // find the root to print logs
            if(threadLocal.get().getExitMethodName() == null) {

                MethodTag exitMethod = methodTag;
                for (int i1 = 2; i1 < stackTrace.length; i1++) {
                    stackTraceElement = stackTrace[i1];
                    String s = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
                    MethodTag methodByName = methodTagMaintainer.getMethodByName(s);
                    if(null == methodByName) {
                        continue;
                    }
                    exitMethod = methodByName;
                }
                threadLocal.get().setExitMethodName(exitMethod.getSimpleMethodDesc());
            }

            long now = System.currentTimeMillis();
            if(threadLocal.get() != null && methodTag.getSimpleMethodDesc().equals(threadLocal.get().getExitMethodName())) {
                StringBuilder sb = new StringBuilder();
                sb.append(BannerUtils.buildBanner("MonitorJ Method ", now - spend, now, true));
                sb.append(threadLocal.get().getSb().toString());
                threadLocal.get().getSb().setLength(0);
                threadLocal.get().setExitMethodName(null);
                Recorders.getInstance().record(new Recorder(logger, true, sb.toString()));
            }
        } catch (Exception e) {
            Logger.error("ProfilingAspect.profiling(" + startNanos + ", " + methodTagId + ", "
                    + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ")", e);
        }
    }

}
