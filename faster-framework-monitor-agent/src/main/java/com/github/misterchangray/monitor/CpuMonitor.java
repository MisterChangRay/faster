package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
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
            sb.append("MonitorJ CPU [").append(DateFormatUtils.format(startMillis)).append(", ")
                    .append(DateFormatUtils.format(stopMillis)).append(']').append(Consts.LINE_SEPARATOR);

            String format = String.format("application: %s, Pid: %s, cpuUsage: %s %% !",
                    ProfilingConfig.getMonitorConfig().getAppName(),
                    ProfilingConfig.getMonitorConfig().getProcessId(),
                    (int) cpuUsed);

            sb.append(format);

            Recorder recorder = new Recorder(logger, false, sb.toString());

            if(cpuUsed > ProfilingConfig.getMonitorConfig().getMaxCpuUsedOfProcess()) {
                recorder.setNotify(true);
            }
            Recorders.record(recorder);
        }

    }
}