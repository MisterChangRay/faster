package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.BannerUtils;
import com.github.misterchangray.monitor.utils.DateFormatUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by ray.chang on 2022/01/19
 */
public final class ProfilingExceptionAspect {
    static ILogger logger = LoggerFactory.getLogger("monitor-exceptions.log");

    private static final MethodTagMaintainer methodTagMaintainer = MethodTagMaintainer.getInstance();

    private ProfilingExceptionAspect() {
        //empty
    }

    private static long millis = 1000000;
    private static long sec = 1000 * millis;

    public static void profiling(Throwable throwable, int methodTagId) {
        long startMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();

        long stopMillis = System.currentTimeMillis();
        sb.append(BannerUtils.buildBanner("MonitorJ Exception Occurred [", startMillis, stopMillis));
        sb.append(format(throwable));

        Recorder recorder = new Recorder(logger, ProfilingConfig.getMonitorConfig().isNotifyExceptions(), sb.toString());
        Recorders.record(recorder);
    }
    /**
     * 将异常信息转化成字符串
     * @param t
     * @return
     * @throws IOException
     */
    private static String format(Throwable t)  {
        try {
            if(t == null)
                return null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try{
                t.printStackTrace(new PrintStream(baos));
            }finally{
                baos.close();
            }
            return baos.toString();
        } catch (Exception ae) {}
        return "";
    }

}
