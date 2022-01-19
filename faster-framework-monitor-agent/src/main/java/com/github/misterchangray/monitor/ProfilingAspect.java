package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.DateFormatUtils;
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
            String msg =  "[" + DateFormatUtils.format(System.currentTimeMillis()) + "] " +
                    methodTagMaintainer.getMethodTag(methodTagId).getFullDesc() + ": " + spend;
            Recorders.record(new Recorder(logger, true, msg));
        } catch (Exception e) {
            Logger.error("ProfilingAspect.profiling(" + startNanos + ", " + methodTagId + ", "
                    + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ")", e);
        }
    }


}
