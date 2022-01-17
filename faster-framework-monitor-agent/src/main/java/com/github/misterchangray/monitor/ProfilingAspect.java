package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.utils.Logger;

/**
 * Created by LinShunkang on 2018/4/15
 */
public final class ProfilingAspect {

    private static final MethodTagMaintainer methodTagMaintainer = MethodTagMaintainer.getInstance();

    private ProfilingAspect() {
        //empty
    }

    private static long millis = 1000000;
    public static void profiling(final long startNanos, final int methodTagId) {
        try {
            long i = (System.nanoTime() - startNanos);
            if(i == 0) return;
            long j = i > millis ? i / millis : i;
            if(i == j) return;

            Logger.info(methodTagMaintainer.getMethodTag(methodTagId).getFullDesc() + ":" + j);
        } catch (Exception e) {
            Logger.error("ProfilingAspect.profiling(" + startNanos + ", " + methodTagId + ", "
                    + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ")", e);
        }
    }


}
