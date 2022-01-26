package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.BannerUtils;
import com.github.misterchangray.monitor.utils.Logger;

public class MemoryMonitor implements Runnable {
    public static ILogger logger = LoggerFactory.getLogger("monitor-memory.log");
    private static long lastTime = 0;
    private static long sec15 = 15 * 1000;

    @Override
    public void run() {
        Logger.debug("--->>> Start MemoryMonitor");
        long startMillis = System.currentTimeMillis();

        CustomConfig customConfig = ProfilingConfig.getCustomConfig();
        long noneHeapMemoryUsage = JSystem.getNoneHeapMemoryUsage();
        long heapMemoryUsage = JSystem.getHeapMemoryUsage();
        long total = noneHeapMemoryUsage + heapMemoryUsage;

        long stopMillis = System.currentTimeMillis();


        if(heapMemoryUsage > customConfig.getMaxHeapUseKb() || noneHeapMemoryUsage > customConfig.getMaxNonHeapUseKb() ) {
            boolean sentAble = stopMillis - lastTime > sec15 ? true : false;
            if(sentAble) {
                lastTime = stopMillis;
            }

            StringBuilder sb = new StringBuilder(256);
            sb.append(BannerUtils.buildBanner("MonitorJ Memory ", startMillis, stopMillis));

            String format = String.format("memoryTotalUsage: %s kb, heap: %s kb, nonHeap: %s kb , thresholdHeap: %s kb, thresholdNoneHeap: %s kb!",
                    (total / 1024), heapMemoryUsage / 1024, noneHeapMemoryUsage / 1024,
                    customConfig.getMaxHeapUseKb() / 1024, customConfig.getMaxNonHeapUseKb() / 1026);

            sb.append(format);
            Recorders.getInstance().record(new Recorder(logger,  sentAble, sb.toString()));
        }

    }
}
