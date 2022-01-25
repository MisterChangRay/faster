package com.github.misterchangray.monitor.controler.pojo;

public class HeartBeat {
    private String appName;
    private String projectPath;
    private String processId;
    private long timestamp;
    private String dingdingUrl;
    private String dingdingSec;

    public String getDingdingUrl() {
        return dingdingUrl;
    }

    public void setDingdingUrl(String dingdingUrl) {
        this.dingdingUrl = dingdingUrl;
    }

    public String getDingdingSec() {
        return dingdingSec;
    }

    public void setDingdingSec(String dingdingSec) {
        this.dingdingSec = dingdingSec;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
