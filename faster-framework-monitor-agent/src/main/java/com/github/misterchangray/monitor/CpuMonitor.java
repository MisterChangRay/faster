package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.BannerUtils;
import com.github.misterchangray.monitor.utils.DateFormatUtils;

public class CpuMonitor implements Runnable {
    static ILogger logger = LoggerFactory.getLogger("monitor-cpu.log");

    @Override
    public void run() {
        long startMillis = System.currentTimeMillis();

        double cpuUsed = JSystem.getCpuUsed() * 100;

        long stopMillis = System.currentTimeMillis();
        if(cpuUsed > 50) {
            StringBuilder sb = new StringBuilder(256);
            sb.append(BannerUtils.buildBanner("MonitorJ CPU ", startMillis, stopMillis));

            String format = String.format("application: %s, Pid: %s, cpuUsage: %s %% , threshold: %s %%!",
                    ProfilingConfig.getCustomConfig().getAppName(),
                    ProfilingConfig.getMonitorConfig().getProcessId(),
                    (int) cpuUsed,
                    ProfilingConfig.getCustomConfig().getMaxCpuUsedOfProcess());

            sb.append(format);

            Recorder recorder = new Recorder(logger, false, sb.toString());

            if(cpuUsed > ProfilingConfig.getCustomConfig().getMaxCpuUsedOfProcess()) {
                recorder.setNotify(true);
            }
            Recorders.getInstance().record(recorder);
        }

    }
}
