package com.github.misterchangray.monitor.config;

import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by rui.chang on 2022/1/12
 */
public final class ProfilingConfig {
    private static MonitorConfig monitorConfig;
    private static CustomConfig customConfig;

    public static String fileLocation(MonitorConfig monitorConfig, String customFilePath) {
        if(Objects.nonNull(customFilePath) && customFilePath.length() > 3) {
            boolean exists = new File(customFilePath).exists();
            if(!exists) {
                throw new RuntimeException("error configuration location:" + customFilePath);
            }
            return customFilePath;
        }

       return monitorConfig.getProjectPath() + Consts.FILE_SEPARATOR + "monitorj.properties";
    }

    public static boolean initProperties(String configFile) {
        MonitorConfig monitorConfig = new MonitorConfig();
        String jarpath = ProfilingConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String projectPath = System.getProperty("user.dir");
        monitorConfig.setJarPath(jarpath);
        monitorConfig.setProjectPath(projectPath);

        String configFilePath = fileLocation(monitorConfig, configFile);
        monitorConfig.setConfigPath(configFilePath);

        try (InputStream in = new FileInputStream(configFilePath)) {
            Properties properties = new Properties();
            properties.load(in);


            if(!initMonitorConfig(monitorConfig, properties, jarpath)) {
                return false;
            }

            CustomConfig customConfig = new CustomConfig();
            if(!initCustomConfig(customConfig, properties)) {
                return false;
            }

            return true;
        } catch (IOException e) {
            Logger.error("AbstractBootstrap.initProperties()", e);
        }
        return false;
    }

    private static boolean initCustomConfig(CustomConfig customConfig, Properties properties) {
        customConfig.setMethodsConfig( new HashMap<>());


        customConfig.setNotifyUrlOfDingDing(properties.getOrDefault("notifyUrlOfDingDing", "").toString());
        customConfig.setNotifySecretOfDingDing(properties.getOrDefault("notifySecretOfDingDing", "").toString());
        customConfig.setRecordCpuUsage(getBool(properties, "RecordCpuUsage", "true"));
        customConfig.setRecordGC(getBool(properties, "recordGC", "true"));
        customConfig.setRecordMemUsed(getBool(properties, "recordMemUsed", "true"));
        customConfig.setNotifyExceptions(getBool(properties, "notifyExceptions", "false"));
        customConfig.setDebug(getBool(properties, "debug", "false"));
        customConfig.setAppName(properties.getOrDefault("appName", "").toString());

        for (Object o : properties.keySet()) {
            String key = o.toString().toUpperCase();
            if(!key.startsWith("method")) {
                continue;
            }

            MethodConfig methodConfig = new MethodConfig();
            if(key.endsWith(".name")) {
                String prefix = key.substring(0, key.length() - 5);

                methodConfig.setMethodFullName(properties.getProperty(key));
                methodConfig.setTtlOfSec((int) getNumber(properties, prefix+".ttl", "3", 1l));
            }

            customConfig.getMethodsConfig().put(methodConfig.getMethodFullName(), methodConfig);
        }
        return true;
    }


    /**
     * 重新加载自定义配置
     */
    public static void reloadCustomConfig() {
        CustomConfig tmp = new CustomConfig();
        boolean hasError = false;
        try (InputStream in = new FileInputStream(getMonitorConfig().getConfigPath())) {
            Properties properties = new Properties();
            properties.load(in);

            initCustomConfig(tmp, properties);
        } catch (IOException e) {
            hasError = true;
        } finally {
            if(hasError == false) {
                customConfig = tmp;
            }
        }
    }

    public static MonitorConfig getMonitorConfig() {
        return monitorConfig;
    }

    public static void setMonitorConfig(MonitorConfig monitorConfig) {
        ProfilingConfig.monitorConfig = monitorConfig;
    }

    public static CustomConfig getCustomConfig() {
        return customConfig;
    }

    public static void setCustomConfig(CustomConfig customConfig) {
        ProfilingConfig.customConfig = customConfig;
    }

    private static boolean initMonitorConfig(MonitorConfig monitorConfig, Properties properties, String jarpath) {
        monitorConfig.setJarPath(jarpath );


        monitorConfig.setMonitorPackage(properties.getOrDefault("scanPackage", "").toString());
        if("".equals(monitorConfig.getMonitorPackage())) {
            return false;
        }

        if(monitorConfig.getMonitorPackage().length() > 0 && monitorConfig.getMonitorPackage().contains(".")) {
            monitorConfig.setMonitorPackage(monitorConfig.getMonitorPackage().replaceAll("\\.", "/"));
        } else {
            throw new RuntimeException("scanPackage config error; like com.test.target ");
        }


        monitorConfig.setLogPath(properties.getOrDefault("logPath", "").toString());
        if(monitorConfig.getLogPath().length() < 3) {
            String path = monitorConfig.getJarPath() + "monitorJLogs" + Consts.FILE_SEPARATOR;
            monitorConfig.setLogPath(path);
        } else if(monitorConfig.getLogPath().length() > 3 && !monitorConfig.getLogPath().endsWith(Consts.FILE_SEPARATOR)) {
            monitorConfig.setLogPath(monitorConfig.getLogPath() + Consts.FILE_SEPARATOR);
        }


        monitorConfig.setMaxTTLOfSec((int) getNumber(properties, "maxTTLOfSec", "3", 1L));
        monitorConfig.setMaxCpuUsedOfProcess((int) getNumber(properties, "maxCpuUsedOfProcess", "80"));
        monitorConfig.setMaxThreadOfProcess((int) getNumber(properties, "maxThreadOfProcess", "5000"));
        monitorConfig.setMaxExceptions((int) getNumber(properties, "maxExceptions", "50"));

        // 默认取配置最大堆内存 + 300MB堆外内存
        long defaultMax = Runtime.getRuntime().maxMemory() + 314572800;
        monitorConfig.setMaxMemUseKb(getNumber(properties, "maxMemUse", defaultMax + ""));
        monitorConfig.setProcessId(getProcessID() + "");

        return true;
    }


    private static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }



    private static boolean getBool(Properties properties, String key, String defaultValue) {
        String tmp = properties.getOrDefault(key, defaultValue).toString().toUpperCase();
        if("TRUE".equals(tmp) || "FALSE".equals(tmp)) {
            return Boolean.valueOf(tmp);
        } else {
            throw new RuntimeException(key + " config error, must is bool");
        }
    }

    private static long getNumber(Properties properties, String key, String defaultValue) {
        return getNumber(properties, key, defaultValue, null);
    }

    private static long getNumber(Properties properties, String key, String defaultValue, Long minVal) {
        String tmp = properties.getOrDefault(key, defaultValue).toString();
        if(tmp.matches("^\\d+$")) {
            long l = Long.valueOf(tmp);
            if(minVal == null) {
                return l;
            }
            return l < minVal ? minVal : l;
        } else {
            throw new RuntimeException(key + " config error, must is number");
        }
    }


}
