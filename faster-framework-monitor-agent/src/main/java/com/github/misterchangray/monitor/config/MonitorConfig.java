package com.github.misterchangray.monitor.config;



/**
 * Created by rui.chang on 2022/01/18
 */
public class MonitorConfig {
    /**
     * 监控包路径
     */
    private String monitorPackage;

    /**
     * 钉钉HTTP通知地址
     */
    private String notifyUrlOfDingDing;

    /**
     * 方法超时时间
     */
    private int maxTTLOfSec;

    /**
     * 日志记录路径
     */
    private String logPath;


    /**
     * 是否记录CPU使用率
     *
     */
    private boolean recordCpuUsage;

    /**
     * 进程使用最大CPU通知阈值
     * 进程使用CPU超过此值后发送通知
     * 默认为90
     */
    private int maxCpuUsedOfProcess;

    /**
     * 打印GC情况
     * 每 5/s 打印一次GC情况
     */
    private boolean recordGC;

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
     * 是否记录内存使用情况
     */
    private boolean recordMemUsed;

    /**
     * 内存使用超过此阈值后发送通知
     *
     * 默认为 500MB
     */
    private long maxMemUse;

    /**
     * 进程ID
     */
    private String processId;


    /**
     * 应用名称
     */
    private String appName;


    public String getMonitorPackage() {
        return monitorPackage;
    }

    public void setMonitorPackage(String monitorPackage) {
        this.monitorPackage = monitorPackage;
    }

    public String getNotifyUrlOfDingDing() {
        return notifyUrlOfDingDing;
    }

    public void setNotifyUrlOfDingDing(String notifyUrlOfDingDing) {
        this.notifyUrlOfDingDing = notifyUrlOfDingDing;
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

    public boolean isRecordCpuUsage() {
        return recordCpuUsage;
    }

    public void setRecordCpuUsage(boolean recordCpuUsage) {
        this.recordCpuUsage = recordCpuUsage;
    }

    public int getMaxCpuUsedOfProcess() {
        return maxCpuUsedOfProcess;
    }

    public void setMaxCpuUsedOfProcess(int maxCpuUsedOfProcess) {
        this.maxCpuUsedOfProcess = maxCpuUsedOfProcess;
    }

    public boolean isRecordGC() {
        return recordGC;
    }

    public void setRecordGC(boolean recordGC) {
        this.recordGC = recordGC;
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

    public boolean isRecordMemUsed() {
        return recordMemUsed;
    }

    public void setRecordMemUsed(boolean recordMemUsed) {
        this.recordMemUsed = recordMemUsed;
    }


    public long getMaxMemUse() {
        return maxMemUse;
    }

    public void setMaxMemUse(long maxMemUse) {
        this.maxMemUse = maxMemUse;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
