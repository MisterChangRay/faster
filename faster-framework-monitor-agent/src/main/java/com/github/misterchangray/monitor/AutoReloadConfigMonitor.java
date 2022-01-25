package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.utils.Logger;

public class AutoReloadConfigMonitor implements Runnable {


    @Override
    public void run() {
       try {
           Logger.debug("--->>> Start AutoReloadConfigMonitor; path: " + ProfilingConfig.getMonitorConfig().getConfigPath());
           ProfilingConfig.reloadCustomConfig();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
