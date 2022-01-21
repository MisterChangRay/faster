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

public class MemoryMonitor implements Runnable {
    public static ILogger logger = LoggerFactory.getLogger("monitor-memory.log");


    @Override
    public void run() {
        long startMillis = System.currentTimeMillis();

        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();
        long noneHeapMemoryUsage = JSystem.getNoneHeapMemoryUsage();
        long heapMemoryUsage = JSystem.getHeapMemoryUsage();
        long total = noneHeapMemoryUsage + heapMemoryUsage;

        long stopMillis = System.currentTimeMillis();
        if(total > monitorConfig.getMaxMemUseKb()) {
            StringBuilder sb = new StringBuilder(256);
            sb.append(BannerUtils.buildBanner("MonitorJ Memory ", startMillis, stopMillis));

            String format = String.format("application: %s, Pid: %s, memoryTotalUsage: %s kb, heap: %s, nonHeap: %s !",
                    ProfilingConfig.getCustomConfig().getAppName(),
                    ProfilingConfig.getMonitorConfig().getProcessId(),
                    (total / 1024), heapMemoryUsage / 1024, noneHeapMemoryUsage / 1024);

            sb.append(format);
            Recorders.getInstance().record(new Recorder(logger,  true, sb.toString()));
        }

    }
}
