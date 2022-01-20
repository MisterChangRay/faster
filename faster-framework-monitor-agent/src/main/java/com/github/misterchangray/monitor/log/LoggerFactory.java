package com.github.misterchangray.monitor.log;

import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;

import java.util.HashMap;
import java.util.Map;

public final class LoggerFactory {

    private static final MonitorConfig MONITOR_CONFIG = ProfilingConfig.getMonitorConfig();

    private static final Map<String, ILogger> LOGGER_MAP = new HashMap<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                for (ILogger writer : LOGGER_MAP.values()) {
                    writer.preCloseLog();
                }

                for (ILogger writer : LOGGER_MAP.values()) {
                    writer.closeLog();
                }
            }
        }));
    }

    private LoggerFactory() {
        //empty
    }

    public static synchronized ILogger getLogger(String logFile) {
        logFile = logFile.trim();

        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();
        String path = monitorConfig.getJarPath();
        if(monitorConfig.getLogPath().length() > 3) {
            path = monitorConfig.getLogPath();
        }
        ILogger logger = new AutoRollingLogger( path + logFile , 10);
        LOGGER_MAP.put(logFile, logger);
        return logger;
    }
}
