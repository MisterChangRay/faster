package com.github.misterchangray.monitor.config;

import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;

/**
 * Created by rui.chang on 2022/1/12
 */
public final class ProfilingConfig {

    private static MonitorConfig CONFIG;

    public static MonitorConfig getMonitorConfig() {
        return CONFIG;

    }


    public static void setCONFIG(MonitorConfig CONFIG) {
        ProfilingConfig.CONFIG = CONFIG;
    }


    public static boolean initProperties() {
        String jarpath = ProfilingConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(jarpath);
        jarpath = file.getParent() + File.separator;

        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setJarPath(jarpath );

        try (InputStream in = new FileInputStream(jarpath + "monitorj.properties")) {
            Properties properties = new Properties();
            properties.load(in);

            monitorConfig.setNotifyUrlOfDingDing(properties.getOrDefault("notifyUrlOfDingDing", "").toString());
            monitorConfig.setNotifySecretOfDingDing(properties.getOrDefault("notifySecretOfDingDing", "").toString());

            monitorConfig.setMonitorPackage(properties.getOrDefault("scanPackage", "").toString());
            if("".equals(monitorConfig.getMonitorPackage()) ||
                    monitorConfig.getMonitorPackage().startsWith("/") ||
                    monitorConfig.getMonitorPackage().endsWith("/")) {
                throw new RuntimeException("scanPackage config error, must not start and end with /");
            }
            if(monitorConfig.getMonitorPackage().contains(".")) {
                monitorConfig.setMonitorPackage(monitorConfig.getMonitorPackage().replaceAll("\\.", "/"));
            }


            monitorConfig.setLogPath(properties.getOrDefault("logPath", "").toString());
            if(monitorConfig.getLogPath().length() < 3) {
                String path = monitorConfig.getJarPath() + "monitorJLogs" + Consts.FILE_SEPARATOR;
                monitorConfig.setLogPath(path);
            } else if(monitorConfig.getLogPath().length() > 3 && !monitorConfig.getLogPath().endsWith(Consts.FILE_SEPARATOR)) {
                monitorConfig.setLogPath(monitorConfig.getLogPath() + Consts.FILE_SEPARATOR);
            }

            monitorConfig.setRecordCpuUsage(getBool(properties, "RecordCpuUsage", "true"));
            monitorConfig.setRecordGC(getBool(properties, "recordGC", "true"));
            monitorConfig.setRecordMemUsed(getBool(properties, "recordMemUsed", "true"));
            monitorConfig.setNotifyExceptions(getBool(properties, "notifyExceptions", "false"));
            monitorConfig.setDebug(getBool(properties, "debug", "false"));

            monitorConfig.setMaxTTLOfSec((int) getNumber(properties, "maxTTLOfSec", "3"));
            monitorConfig.setMaxCpuUsedOfProcess((int) getNumber(properties, "maxCpuUsedOfProcess", "80"));
            monitorConfig.setMaxThreadOfProcess((int) getNumber(properties, "maxThreadOfProcess", "5000"));
            monitorConfig.setMaxExceptions((int) getNumber(properties, "maxExceptions", "50"));

            // 默认取配置最大堆内存 + 100MB堆外内存
            long defaultMax = Runtime.getRuntime().maxMemory() + 104857600;
            monitorConfig.setMaxMemUseKb(getNumber(properties, "maxMemUse", defaultMax + ""));
            monitorConfig.setProcessId(getProcessID() + "");

            monitorConfig.setAppName(properties.getOrDefault("appName", "").toString());
            ProfilingConfig.setCONFIG(monitorConfig);
            return true;
        } catch (IOException e) {
            Logger.error("AbstractBootstrap.initProperties()", e);
        }
        return false;
    }




    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }



    public static boolean getBool(Properties properties, String key, String defaultValue) {
        String tmp = properties.getOrDefault(key, defaultValue).toString().toUpperCase();
        if("TRUE".equals(tmp) || "FALSE".equals(tmp)) {
            return Boolean.valueOf(tmp);
        } else {
            throw new RuntimeException(key + " config error, must is bool");
        }
    }

    public static long getNumber(Properties properties, String key, String defaultValue) {
        String tmp = properties.getOrDefault(key, defaultValue).toString();
        if(tmp.matches("^\\d+$")) {
            return Long.valueOf(tmp);
        } else {
            throw new RuntimeException(key + " config error, must is number");
        }
    }

}
