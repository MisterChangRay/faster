package com.github.misterchangray.monitor.log;


import com.github.misterchangray.monitor.file.AutoRollingFileWriter;
import com.github.misterchangray.monitor.file.DailyRollingFileWriter;


public class AutoRollingLogger implements ILogger {

    private static final String LINE_SEPARATOR = "\r\n";
    private final AutoRollingFileWriter writer;

    AutoRollingLogger(String logFile,  int reserveFileCount) {
        this.writer = new DailyRollingFileWriter(logFile, reserveFileCount);

    }

    @Override
    public void log(String msg) {
        writer.write(msg + LINE_SEPARATOR);
    }

    @Override
    public void logAndFlush(String msg) {
        writer.writeAndFlush(msg + LINE_SEPARATOR);
    }

    @Override
    public void flushLog() {
        writer.flush();
    }

    @Override
    public void preCloseLog() {
        writer.preCloseFile();
    }

    @Override
    public void closeLog() {
        writer.closeFile(true);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        writer.closeFile(true);
    }
}
