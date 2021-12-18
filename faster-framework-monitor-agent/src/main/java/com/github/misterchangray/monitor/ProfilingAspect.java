package com.github.misterchangray.monitor;

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
            long j = i > millis ? i / millis : i;
            if(i == j) return;

            MyLogger.info(methodTagMaintainer.getMethodTag(methodTagId).getFullDesc() + ":" + j);
        } catch (Exception e) {
            MyLogger.error("ProfilingAspect.profiling(" + startNanos + ", " + methodTagId + ", "
                    + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ")", e);
        }
    }


}
