package com.github.misterchangray.monitor.utils;

import com.github.misterchangray.common.consts.SystemConst;
import com.github.misterchangray.common.util.ThreadUtils;
import com.github.misterchangray.monitor.Notify;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.notifys.DingDingNotify;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ray.chang on 2022/1/15
 */
public final class Logger {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor(ThreadUtils.newThreadFactory("MonitorJ-Logger-"));
    private static Notify notify = new DingDingNotify();

    private static final ThreadLocal<DateFormat> TO_MILLS_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
    };

    private static boolean debugEnable;

    private static final String PREFIX = " [MonitorJ] ";

    private static final String INFO_LEVEL = "INFO ";

    private static final String DEBUG_LEVEL = "DEBUG ";

    private static final String WARN_LEVEL = "WARN ";

    private static final String ERROR_LEVEL = "ERROR ";

    private Logger() {
        //empty
    }



    public static void setDebugEnable(boolean debugEnable) {
        Logger.debugEnable = debugEnable;
    }

    public static boolean isDebugEnable() {
        return debugEnable;
    }


    public static void info(String msg) {
        System.out.println(getPrefix(INFO_LEVEL) + msg);
    }

    private static String getPrefix(String logLevel) {
        return getToMillisStr(new Date()) + PREFIX + logLevel + "[" + Thread.currentThread().getName() + "] ";
    }

    private static String getToMillisStr(Date date) {
        return TO_MILLS_DATE_FORMAT.get().format(date);
    }

    public static void debug(String msg) {
        if (debugEnable) {
            System.out.println(getPrefix(DEBUG_LEVEL) + msg);
        }
    }

    public static void warn(String msg) {
        System.out.println(getPrefix(WARN_LEVEL) + msg);
    }

    public static void error(String msg) {
        System.err.println(getPrefix(ERROR_LEVEL) + msg);
    }

    public static void error(String msg, Throwable throwable) {
        synchronized (System.err) {
            System.err.println(getPrefix(ERROR_LEVEL) + msg + " " + throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    /**
     * 总共4个线程,
     */
    public static void initLogger() {
        notify.init();

        executor.execute(() -> {
            Recorder[] recorders = null;
            StringBuilder sb = null;
            Recorder fetch = null;
            try {
                while (true) {
                    recorders = Recorders.getInstance().fetch();
                    if(null == recorders) {
                        Thread.sleep(10);
                        continue;
                    }

                    sb = new StringBuilder();
                    for (int i = 0; i < recorders.length; i++) {
                        fetch = recorders[i];
                        if(null == fetch) continue;


                        if(ProfilingConfig.getCustomConfig().isDebug()) {
                            Logger.info(fetch.getMsg());
                        }

                        fetch.getiLogger().logAndFlush(fetch.getMsg());
                        if(fetch.isNotify()) {
                            sb.append(fetch.getMsg() + SystemConst.LINE_SEPARATOR);
                        }
                    }

                    if(sb.length() > 0) {
                        notify.notify(sb);
                    }
                }
            } catch (Exception e) { }
        });


    }
}
