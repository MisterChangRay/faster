package com.github.misterchangray.monitor.stacktrace;

public class StackTraceElementExt {
    private String exitMethodName;
    private StringBuilder sb;

    public StackTraceElementExt() {
       this.sb = new StringBuilder();
       this.exitMethodName = null;
    }

    public String getExitMethodName() {
        return exitMethodName;
    }

    public void setExitMethodName(String exitMethodName) {
        this.exitMethodName = exitMethodName;
    }

    public StringBuilder getSb() {
        return sb;
    }

    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }
}
