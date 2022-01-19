package com.github.misterchangray.monitor.log;

import java.util.Date;

public class Recorder {
    boolean notify;
    String msg;
    Date createDate;
    ILogger iLogger;

    public Recorder(ILogger iLogger, boolean notify, String msg) {
        this.notify = notify;
        this.iLogger = iLogger;
        this.msg = msg;
        this.createDate = new Date();
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ILogger getiLogger() {
        return iLogger;
    }

    public void setiLogger(ILogger iLogger) {
        this.iLogger = iLogger;
    }
}
