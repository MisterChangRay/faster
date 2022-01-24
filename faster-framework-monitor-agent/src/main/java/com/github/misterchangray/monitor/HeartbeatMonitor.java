package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.utils.HttpClient;

public class HeartbeatMonitor implements Runnable {


    @Override
    public void run() {
        CustomConfig customConfig = ProfilingConfig.getCustomConfig();
        if(customConfig.getServerAddr().length() < 3) {
            return;
        }


        String url = customConfig.getServerAddr();
        if(!customConfig.getServerAddr().endsWith("/")) {
            url = url + "/";
        }
        url = url + "/heartbeat";

        StringBuilder param = new StringBuilder();
        param.append("{");
        param.append("\"appName\":\"" + ProfilingConfig.getCustomConfig().getAppName() + "\",");
        param.append("\"processId\":\"" + ProfilingConfig.getMonitorConfig().getProcessId() + "\",");
        param.append("\"projectPath\":\"" + ProfilingConfig.getMonitorConfig().getProjectPath() + "\",");
        param.append("\"timestamp\":\"" + System.currentTimeMillis() + "\"");
        param.append("}");

        HttpClient.executePost(url, param.toString());
    }

}
