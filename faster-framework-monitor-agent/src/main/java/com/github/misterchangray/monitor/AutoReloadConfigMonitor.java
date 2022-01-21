package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.ProfilingConfig;

public class AutoReloadConfigMonitor implements Runnable {


    @Override
    public void run() {
       while (true) {
           try {
               ProfilingConfig.reloadCustomConfig();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }

    }
}
