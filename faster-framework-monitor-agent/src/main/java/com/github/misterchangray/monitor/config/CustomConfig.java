package com.github.misterchangray.monitor.config;

import java.util.HashMap;
import java.util.Map;

public class CustomConfig {
    private Map<String, MethodConfig> methodsConfig = new HashMap<>();

    /**
     * 钉钉HTTP通知地址
     */
    private String notifyUrlOfDingDing;

    private String notifySecretOfDingDing;


    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用名称
     */
    private boolean debug;


    /**
     * 是否记录CPU使用率
     *
     */
    private boolean recordCpuUsage;

    /**
     * 是否记录线程情况
     *
     */
    private boolean recordThread;

    /**
     * 打印GC情况
     * 每 5/s 打印一次GC情况
     */
    private boolean recordGC;

    /**
     * 是否记录内存使用情况
     */
    private boolean recordMemUsed;

    /**
     * 是否推送异常
     */
    private boolean notifyExceptions;



    /**
     * 方法超时时间
     */
    private int maxTTLOfSec;



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
     * 堆内存使用超过后预警
     *
     * 默认为 500MB
     */
    private long maxHeapUseKb;

    /**
     * 堆内存使用超过后预警
     *
     * 默认为 500MB
     */
    private long maxNonHeapUseKb;



    /**
     * 监控服务中心地址
     */
    private String serverAddr;

    public boolean isRecordThread() {
        return recordThread;
    }

    public void setRecordThread(boolean recordThread) {
        this.recordThread = recordThread;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public int getMaxTTLOfSec() {
        return maxTTLOfSec;
    }

    public void setMaxTTLOfSec(int maxTTLOfSec) {
        this.maxTTLOfSec = maxTTLOfSec;
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

    public long getMaxHeapUseKb() {
        return maxHeapUseKb;
    }

    public void setMaxHeapUseKb(long maxHeapUseKb) {
        this.maxHeapUseKb = maxHeapUseKb;
    }

    public long getMaxNonHeapUseKb() {
        return maxNonHeapUseKb;
    }

    public void setMaxNonHeapUseKb(long maxNonHeapUseKb) {
        this.maxNonHeapUseKb = maxNonHeapUseKb;
    }

    public Map<String, MethodConfig> getMethodsConfig() {
        return methodsConfig;
    }

    public void setMethodsConfig(Map<String, MethodConfig> methodsConfig) {
        this.methodsConfig = methodsConfig;
    }

    public String getNotifyUrlOfDingDing() {
        return notifyUrlOfDingDing;
    }

    public void setNotifyUrlOfDingDing(String notifyUrlOfDingDing) {
        this.notifyUrlOfDingDing = notifyUrlOfDingDing;
    }

    public String getNotifySecretOfDingDing() {
        return notifySecretOfDingDing;
    }

    public void setNotifySecretOfDingDing(String notifySecretOfDingDing) {
        this.notifySecretOfDingDing = notifySecretOfDingDing;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isRecordCpuUsage() {
        return recordCpuUsage;
    }

    public void setRecordCpuUsage(boolean recordCpuUsage) {
        this.recordCpuUsage = recordCpuUsage;
    }

    public boolean isRecordGC() {
        return recordGC;
    }

    public void setRecordGC(boolean recordGC) {
        this.recordGC = recordGC;
    }

    public boolean isRecordMemUsed() {
        return recordMemUsed;
    }

    public void setRecordMemUsed(boolean recordMemUsed) {
        this.recordMemUsed = recordMemUsed;
    }

    public boolean isNotifyExceptions() {
        return notifyExceptions;
    }

    public void setNotifyExceptions(boolean notifyExceptions) {
        this.notifyExceptions = notifyExceptions;
    }


}
