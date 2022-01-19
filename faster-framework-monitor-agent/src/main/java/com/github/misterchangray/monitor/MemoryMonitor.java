package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;

public class MemoryMonitor implements Runnable {
    static ILogger logger = LoggerFactory.getLogger("monitor-memory.log");

    @Override
    public void run() {
        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();
        long noneHeapMemoryUsage = JSystem.getNoneHeapMemoryUsage();
        long heapMemoryUsage = JSystem.getHeapMemoryUsage();
        long total = noneHeapMemoryUsage + heapMemoryUsage;
        if(total > monitorConfig.getMaxMemUse()) {
            String format = String.format("应用: %s, Pid: %s, 当前内存使用已经达到: %s bytes, 可能存在内存泄露, 请注意!",
                    ProfilingConfig.getMonitorConfig().getAppName(),
                    ProfilingConfig.getMonitorConfig().getProcessId(),
                    (noneHeapMemoryUsage));
            Recorders.record(new Recorder(logger,  true, format));
        }

    }
}
