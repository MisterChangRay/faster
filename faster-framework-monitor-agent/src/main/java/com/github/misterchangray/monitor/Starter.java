package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.utils.LightWeightScheduler;
import com.github.misterchangray.monitor.utils.Logger;

import java.lang.instrument.Instrumentation;

public class Starter {

    public static void premain(String agentArgs, Instrumentation inst) {
        if(ProfilingConfig.initProperties()) {
            if(initOtherServers()) {
                inst.addTransformer(new CostTransformer(), true);
            }
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


    /**
     * 初始化其他服务
     * 如:
     * gc 处理
     * 内存监控
     * cpu使用统计
     * @return
     */
    private static boolean initOtherServers() {
        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();
        if(monitorConfig.isRecordCpuUsage()) {
            LightWeightScheduler.exec(new CpuMonitor(), 5);
            LightWeightScheduler.exec(new ThreadMonitor(), 10);
        }
        if(monitorConfig.isRecordMemUsed()) {
            LightWeightScheduler.exec(new MemoryMonitor(), 15);
        }

        if(monitorConfig.isRecordGC()) {
            LightWeightScheduler.exec(new GCMonitor(), 30);
        }

        Logger.startLogger();
        return true;
    }

}