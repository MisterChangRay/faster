package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;

public class CpuMonitor implements Runnable {
    static ILogger logger = LoggerFactory.getLogger("monitor-cpu.log");

    @Override
    public void run() {
        double cpuUsed = JSystem.getCpuUsed();
        if(cpuUsed > 50) {
            String format = String.format("应用: %s, Pid: %s, 当前负载已经达到: %s%, 请注意!",
                    ProfilingConfig.getMonitorConfig().getAppName(),
                    ProfilingConfig.getMonitorConfig().getProcessId(),
                    (int) (cpuUsed * 100));
            Recorder recorder = new Recorder(logger, false, format);

            if(cpuUsed > ProfilingConfig.getMonitorConfig().getMaxCpuUsedOfProcess()) {
                recorder.setNotify(true);
            }
            Recorders.record(recorder);
        }

    }
}
