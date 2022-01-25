package com.github.misterchangray.monitor.utils;

import com.github.misterchangray.common.consts.SystemConst;
import com.github.misterchangray.common.util.DateFormatUtils;
import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;

public class BannerUtils {
    public static String buildBanner(String name, Long start, Long end) {
       return buildBanner(name, start, end, false);
    }

    public static String buildBanner(String name, Long start, Long end, boolean printThreadId) {
        CustomConfig customConfig = ProfilingConfig.getCustomConfig();
        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("[");
        sb.append(customConfig.getAppName());
        sb.append("][");
        if(printThreadId) {
            sb.append(Thread.currentThread().toString());
            sb.append("][");
        }
        sb.append(monitorConfig.getProcessId());
        sb.append("][");
        if(start != null) {
            sb.append(DateFormatUtils.format(start)).append(", ");
        }
        if(end != null) {
            sb.append(DateFormatUtils.format(end)).append(']');
        }
        sb .append(SystemConst.LINE_SEPARATOR);
        return sb.toString();
    }
}
