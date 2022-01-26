package com.github.misterchangray.monitor;

import com.github.misterchangray.common.util.SchedulerExecutor;
import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.utils.Logger;

import java.lang.instrument.Instrumentation;

public class Starter {

    public static void premain(String configFile, Instrumentation inst) {
        if(ProfilingConfig.initProperties(configFile)) {
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
        CustomConfig customConfig = ProfilingConfig.getCustomConfig();
        if(customConfig.isRecordCpuUsage()) {
            CpuMonitor cpuMonitor = new CpuMonitor();
            SchedulerExecutor.execWithFixedDelay(cpuMonitor, 10);

            ThreadMonitor threadMonitor = new ThreadMonitor();
            SchedulerExecutor.execWithFixedDelay(threadMonitor, 60);
        }
        if(customConfig.isRecordMemUsed()) {
            MemoryMonitor memoryMonitor = new MemoryMonitor();
            SchedulerExecutor.execWithFixedDelay(memoryMonitor, 15);
        }

        if(customConfig.isRecordGC()) {
            GCMonitor gcMonitor = new GCMonitor();
            SchedulerExecutor.execWithFixedDelay(gcMonitor, 30);
        }

        if(customConfig.getServerAddr().length() > 3) {
            HeartbeatMonitor heartbeatMonitor = new HeartbeatMonitor();
            SchedulerExecutor.execWithFixedDelay(heartbeatMonitor, 5);
        }

        AutoReloadConfigMonitor autoReloadConfigMonitor = new AutoReloadConfigMonitor();
        SchedulerExecutor.execWithFixedDelay(autoReloadConfigMonitor, 30);

        Logger.initLogger();
        return true;
    }

}