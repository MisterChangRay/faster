package com.github.misterchangray.monitor;

import com.github.misterchangray.common.consts.SystemConst;

import java.lang.management.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JSystem {
    private static JSystem jSystem = new JSystem();

    //运行时情况
    static RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
    //操作系统情况
    static OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
    //线程使用情况
    static ThreadMXBean threads = ManagementFactory.getThreadMXBean();
    //堆内存使用情况
    static MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    //非堆内存使用情况
    static MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
    //类加载情况
    ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
    //内存池对象
    List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
    //编译器和编译情况
    CompilationMXBean cm = ManagementFactory.getCompilationMXBean();
    com.sun.management.OperatingSystemMXBean operatingSystemMXBean =
            (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static StringBuilder findMonitorDeadlockedThreads() {
        long[] monitorDeadlockedThreads = threads.findMonitorDeadlockedThreads();
        if(null == monitorDeadlockedThreads) {
            return null;
        }

        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < monitorDeadlockedThreads.length; i++) {
            ThreadInfo threadInfo = threads.getThreadInfo(monitorDeadlockedThreads[i]);
            sb.append("ThreadInfo" + i  + ": " + threadInfo.toString() );

            int finalI = i;
            allStackTraces.forEach((k, v) -> {
                if(k.getId() == threadInfo.getThreadId() && v != null && v.length > 1) {
                    sb.append("- StackTrace" + finalI + ": " + v[0].toString() + SystemConst.LINE_SEPARATOR + SystemConst.LINE_SEPARATOR);
                }
            });

        }
        return sb;
    }


    public static long getHeapMemoryUsage() {
        //堆内存使用情况
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        return heapMemoryUsage.getUsed();
    }

    public static long getNoneHeapMemoryUsage() {
        //非堆内存使用情况
        MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        return nonHeapMemoryUsage.getUsed();
    }

    public static double getCpuUsed() {
        return jSystem.operatingSystemMXBean.getProcessCpuLoad();
    }

    public static int getCountOfThreadInProcess() {
       return threads.getThreadCount();
    }

}
