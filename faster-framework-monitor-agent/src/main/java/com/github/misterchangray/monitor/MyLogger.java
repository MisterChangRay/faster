package com.github.misterchangray.monitor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * 
 */
public final class MyLogger {
    private static java.util.logging.Logger  logger2 = java.util.logging.Logger.getLogger("FasterMonitor");

    private static boolean debugEnable;

    private MyLogger() {

    }

    public static void setDebugEnable(boolean debugEnable) {
        MyLogger.debugEnable = debugEnable;
    }

    public static boolean isDebugEnable() {
        return debugEnable;
    }

    public static void info(String msg) {
        logger2.info(msg);
    }


    public static void debug(String msg) {
        if (debugEnable) {
            logger2.info( msg);
        }
    }

    public static void warn(String msg) {
        logger2.warning(msg);
    }

    public static void error(String msg){
        logger2.warning(msg);
    }

    public static void error(String msg, Throwable throwable) {
        synchronized (System.err) {
            logger2.log(Level.WARNING, msg, throwable);
            throwable.printStackTrace();
        }
    }
}
