package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.BannerUtils;
import com.github.misterchangray.monitor.utils.DateFormatUtils;
import com.github.misterchangray.monitor.utils.Logger;

public class ThreadMonitor implements Runnable {
    static ILogger logger = LoggerFactory.getLogger("monitor-thread.log");

    @Override
    public void run() {
        Logger.debug("scan thread ");

        long startMillis = System.currentTimeMillis();
        int threadCount = JSystem.getCountOfThreadInProcess();


        StringBuilder s2 = JSystem.findMonitorDeadlockedThreads();

        long stopMillis = System.currentTimeMillis();


        if(s2 != null) {
            StringBuilder sb = new StringBuilder(256);
            sb.append(BannerUtils.buildBanner("MonitorJ Thread DeadLock [", startMillis, stopMillis));

            if(s2 != null) {
                sb.append(s2);
            }
            Recorder recorder = new Recorder(logger, true, sb.toString());
            Recorders.getInstance().record(recorder);
        }

        if(threadCount > 500) {
            StringBuilder sb = new StringBuilder(256);
            sb.append(BannerUtils.buildBanner("MonitorJ Thread Count [", startMillis, stopMillis));
            String format = String.format("current thread count: %s , threshold: %s !", threadCount, ProfilingConfig.getCustomConfig().getMaxThreadOfProcess());

            sb.append(format);
            Recorder recorder = new Recorder(logger, false, sb.toString());

            if(threadCount > ProfilingConfig.getCustomConfig().getMaxThreadOfProcess()) {
                recorder.setNotify(true);
            }
            Recorders.getInstance().record(recorder);
        }

    }
}
