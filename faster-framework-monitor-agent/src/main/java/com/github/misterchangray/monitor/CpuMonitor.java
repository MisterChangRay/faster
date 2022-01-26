package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.BannerUtils;
import com.github.misterchangray.monitor.utils.Logger;

public class CpuMonitor implements Runnable {
    static ILogger logger = LoggerFactory.getLogger("monitor-cpu.log");
    private static long lastTime = 0;
    private static long sec15 = 15 * 1000;

    @Override
    public void run() {
        CustomConfig customConfig = ProfilingConfig.getCustomConfig();
        if(!customConfig.isRecordCpuUsage()) {
            return;
        }

        Logger.debug("--->>> Start CpuMonitor");
        long startMillis = System.currentTimeMillis();

        double cpuUsed = JSystem.getCpuUsed() * 100;

        long stopMillis = System.currentTimeMillis();
        if(cpuUsed > 50) {
            StringBuilder sb = new StringBuilder(256);
            sb.append(BannerUtils.buildBanner("MonitorJ CPU ", startMillis, stopMillis));

            String format = String.format("cpuUsage: %s %% , threshold: %s %%!",
                    (int) cpuUsed,
                    ProfilingConfig.getCustomConfig().getMaxCpuUsedOfProcess());

            sb.append(format);

            Recorder recorder = new Recorder(logger, false, sb.toString());

            boolean sentAble = stopMillis - lastTime > sec15 ? true : false;
            if(sentAble && cpuUsed > ProfilingConfig.getCustomConfig().getMaxCpuUsedOfProcess()) {
                lastTime = stopMillis;
                recorder.setNotify(true);
            }
            Recorders.getInstance().record(recorder);
        }

    }
}
