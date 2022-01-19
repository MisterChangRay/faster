package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.DateFormatUtils;

public class ThreadMonitor implements Runnable {
    static ILogger logger = LoggerFactory.getLogger("monitor-thread.log");

    @Override
    public void run() {
        long startMillis = System.currentTimeMillis();

        int threadCount = JSystem.getCountOfThreadInProcess();


        long stopMillis = System.currentTimeMillis();
        if(threadCount > 500) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("MonitorJ Thread [").append(DateFormatUtils.format(startMillis)).append(", ")
                    .append(DateFormatUtils.format(stopMillis)).append(']').append(Consts.LINE_SEPARATOR);

            String format = String.format("application: %s, Pid: %s, current thread count: %s !",
                    ProfilingConfig.getMonitorConfig().getAppName(),
                    ProfilingConfig.getMonitorConfig().getProcessId(),
                    threadCount);

            sb.append(format);
            Recorder recorder = new Recorder(logger, false, sb.toString());

            if(threadCount > ProfilingConfig.getMonitorConfig().getMaxThreadOfProcess()) {
                recorder.setNotify(true);
            }
            Recorders.record(recorder);
        }

    }
}
