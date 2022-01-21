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
