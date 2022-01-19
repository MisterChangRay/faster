package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;

public class ThreadMonitor implements Runnable {
    static ILogger logger = LoggerFactory.getLogger("monitor-thread.log");

    @Override
    public void run() {
        int threadCount = JSystem.getCountOfThreadInProcess();
        if(threadCount > 500) {
            String format = String.format("应用: %s, Pid: %s, 当前线程数量已经到达: %s, 请注意!",
                    ProfilingConfig.getMonitorConfig().getAppName(),
                    ProfilingConfig.getMonitorConfig().getProcessId(),
                    threadCount);
            Recorder recorder = new Recorder(logger, false, format);

            if(threadCount > ProfilingConfig.getMonitorConfig().getMaxThreadOfProcess()) {
                recorder.setNotify(true);
            }
            Recorders.record(recorder);
        }

    }
}
