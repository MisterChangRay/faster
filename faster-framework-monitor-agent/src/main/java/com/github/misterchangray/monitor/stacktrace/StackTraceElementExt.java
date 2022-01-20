package com.github.misterchangray.monitor.stacktrace;

public class StackTraceElementExt {
    private StackTraceElement[] stackTraceElements;
    private long[] times;

    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

    public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
        this.stackTraceElements = stackTraceElements;
    }

    public long[] getTimes() {
        return times;
    }

    public void setTimes(long[] times) {
        this.times = times;
    }
}
