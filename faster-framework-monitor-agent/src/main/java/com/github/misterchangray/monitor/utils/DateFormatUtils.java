package com.github.misterchangray.monitor.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ray.chang on 2022/1/15
 */
public final class DateFormatUtils {

    private static final ThreadLocal<DateFormat> DEFAULT_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private DateFormatUtils() {
        //empty
    }

    public static String format(long millis) {
        return DEFAULT_DATE_FORMAT.get().format(new Date(millis));
    }
}
