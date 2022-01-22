package com.github.misterchangray.monitor.config;



/**
 * Created by rui.chang on 2022/01/18
 */
public class MonitorConfig {


    /**
     * 日志记录路径
     */
    private String logPath;


    /**
     * 进程ID
     */
    private String processId;


    /**
     * agent 路径
     */
    private String jarPath;

    /**
     * 项目路径
     */
    private String projectPath;


    /**
     * 配置文件路径
     */
    private String configPath;


    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
}
