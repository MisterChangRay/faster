package com.github.misterchangray.monitor.config;

public class MethodConfig {
    private String methodFullName;

    private int ttlOfSec;

    public String getMethodFullName() {
        return methodFullName;
    }

    public void setMethodFullName(String methodFullName) {
        this.methodFullName = methodFullName;
    }

    public int getTtlOfSec() {
        return ttlOfSec;
    }

    public void setTtlOfSec(int ttlOfSec) {
        this.ttlOfSec = ttlOfSec;
    }
}
