package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.utils.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.Properties;

public class Starter {

    public static void premain(String agentArgs, Instrumentation inst) {
        if(initProperties()) {
            inst.addTransformer(new CostTransformer(), true);
        }
    }

    /**
     * 动态 attach 方式启动，运行此方法
     *
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain");
    }

    private static boolean initProperties() {
        try (InputStream in = new FileInputStream("./monitorj.properties")) {
            Properties properties = new Properties();
            properties.load(in);

            MonitorConfig monitorConfig = new MonitorConfig();
            monitorConfig.setDingDingNotify(properties.getOrDefault("dingDingNotify", "").toString());
            monitorConfig.setScanPackage(properties.getOrDefault("scanPackage", "").toString());
            if("".equals(monitorConfig.getScanPackage()) ||
                    monitorConfig.getScanPackage().startsWith("/") ||
                         monitorConfig.getScanPackage().endsWith("/")) {
                throw new RuntimeException("scanPackage config error, must not start and end with /");
            }


            ProfilingConfig.setBasicConfig(monitorConfig);
            return true;
        } catch (IOException e) {
            Logger.error("AbstractBootstrap.initProperties()", e);
        }
        return false;
    }



}