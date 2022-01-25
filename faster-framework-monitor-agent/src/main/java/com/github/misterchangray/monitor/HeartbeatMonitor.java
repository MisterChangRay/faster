package com.github.misterchangray.monitor;

import com.github.misterchangray.common.util.EscapeUtils;
import com.github.misterchangray.common.util.HttpClient;
import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.utils.Logger;

import java.io.IOException;

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
        url = url + "/monitor/heartbeat";


        String path = ProfilingConfig.getMonitorConfig().getProjectPath();
        StringBuilder param = new StringBuilder();
        param.append("{");
        param.append("\"appName\":\"" + EscapeUtils.escapeJson(ProfilingConfig.getCustomConfig().getAppName()) + "\",");
        param.append("\"processId\":\"" + ProfilingConfig.getMonitorConfig().getProcessId() + "\",");
        param.append("\"projectPath\":\"" + EscapeUtils.escapeJson(path) + "\",");
        param.append("\"dingdingUrl\":\"" + EscapeUtils.escapeJson(ProfilingConfig.getCustomConfig().getNotifyUrlOfDingDing()) + "\",");
        param.append("\"dingdingSec\":\"" + ProfilingConfig.getCustomConfig().getNotifySecretOfDingDing() + "\",");
        param.append("\"timestamp\":\"" + System.currentTimeMillis() + "\"");
        param.append("}");


        Logger.debug("start to heartbeat" + param.toString());

        try {
            HttpClient.executePost(url, param.toString());
        } catch (IOException e) {
        }
    }


}
