package com.github.misterchangray.monitor.log;

/**
 * Created by ray.chang on 2022/1/15
 */
public class StdoutLogger implements ILogger {

    @Override
    public void log(String msg) {
        System.out.println(msg);
    }

    @Override
    public void logAndFlush(String msg) {
        log(msg);
    }

    @Override
    public void flushLog() {
        //empty
    }

    @Override
    public void preCloseLog() {
        //empty
    }

    @Override
    public void closeLog() {
        //empty
    }
}
