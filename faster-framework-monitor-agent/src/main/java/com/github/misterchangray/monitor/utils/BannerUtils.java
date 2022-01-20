package com.github.misterchangray.monitor.utils;

import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;

public class BannerUtils {
    public static String buildBanner(String name, Long start, Long end) {
        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("[");
        sb.append(monitorConfig.getAppName());
        sb.append("][");
        sb.append(monitorConfig.getProcessId());
        sb.append("][");
        if(start != null) {
            sb.append(DateFormatUtils.format(start)).append(", ");
        }
        if(end != null) {
            sb.append(DateFormatUtils.format(end)).append(']');
        }
        sb .append(Consts.LINE_SEPARATOR);
        return sb.toString();
    }
}
