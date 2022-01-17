package com.github.misterchangray.monitor.config;

/**
 * Created by LinShunkang on 2018/5/12
 */
public final class ProfilingConfig {

    private static MonitorConfig BASIC_CONFIG;

    public static MonitorConfig getMonitorConfig() {
        return BASIC_CONFIG;

    }


    public static void setBasicConfig(MonitorConfig basicConfig) {
        BASIC_CONFIG = basicConfig;
    }
}
