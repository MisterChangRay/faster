package com.github.misterchangray.monitor.config;



/**
 * Created by LinShunkang on 2020/05/24
 */
public class MonitorConfig {
    private String scanPackage;

    private String dingDingNotify;

    public String getDingDingNotify() {
        return dingDingNotify;
    }

    public void setDingDingNotify(String dingDingNotify) {
        this.dingDingNotify = dingDingNotify;
    }

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }
}
