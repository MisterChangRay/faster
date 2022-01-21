package com.github.misterchangray.monitor.config;



/**
 * Created by rui.chang on 2022/01/18
 */
public class MonitorConfig {
    /**
     * 监控包路径
     * 如: com.github.misterchangray
     * 将会监控以上包路径下的所有类方法
     *
     */
    private String monitorPackage;


    /**
     * 方法超时时间
     */
    private int maxTTLOfSec;

    /**
     * 日志记录路径
     */
    private String logPath;


    /**
     * 进程使用最大CPU通知阈值
     * 进程使用CPU超过此值后发送通知
     * 默认为90
     */
    private int maxCpuUsedOfProcess;



    /**
     * 一个进程最大线程数
     * 当前进程线程数超过此值进行通知
     */
    private int maxThreadOfProcess;


    /**
     * 最大异常
     * 超过此异常阈值发送通知
     */
    private int maxExceptions;



    /**
     * 内存使用超过此阈值后发送通知
     *
     * 默认为 500MB
     */
    private long maxMemUseKb;

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

    public String getMonitorPackage() {
        return monitorPackage;
    }

    public void setMonitorPackage(String monitorPackage) {
        this.monitorPackage = monitorPackage;
    }

    public int getMaxTTLOfSec() {
        return maxTTLOfSec;
    }

    public void setMaxTTLOfSec(int maxTTLOfSec) {
        this.maxTTLOfSec = maxTTLOfSec;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public int getMaxCpuUsedOfProcess() {
        return maxCpuUsedOfProcess;
    }

    public void setMaxCpuUsedOfProcess(int maxCpuUsedOfProcess) {
        this.maxCpuUsedOfProcess = maxCpuUsedOfProcess;
    }

    public int getMaxThreadOfProcess() {
        return maxThreadOfProcess;
    }

    public void setMaxThreadOfProcess(int maxThreadOfProcess) {
        this.maxThreadOfProcess = maxThreadOfProcess;
    }

    public int getMaxExceptions() {
        return maxExceptions;
    }

    public void setMaxExceptions(int maxExceptions) {
        this.maxExceptions = maxExceptions;
    }

    public long getMaxMemUseKb() {
        return maxMemUseKb;
    }

    public void setMaxMemUseKb(long maxMemUseKb) {
        this.maxMemUseKb = maxMemUseKb;
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
