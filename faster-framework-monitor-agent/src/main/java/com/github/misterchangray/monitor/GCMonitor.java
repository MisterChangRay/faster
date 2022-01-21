package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.ILogger;
import com.github.misterchangray.monitor.log.LoggerFactory;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.pojo.JvmGcMetrics;
import com.github.misterchangray.monitor.pojo.JvmMemoryMetrics;
import com.github.misterchangray.monitor.utils.BannerUtils;
import com.github.misterchangray.monitor.utils.DateFormatUtils;
import com.github.misterchangray.monitor.utils.Logger;
import com.github.misterchangray.monitor.utils.SetUtils;

import java.lang.management.*;
import java.util.List;
import java.util.Set;

public class GCMonitor implements Runnable {
    static ILogger logger = LoggerFactory.getLogger("monitor-gc.log");

    private static final Set<String> YOUNG_GC_SET = SetUtils.of(
            "Copy",
            "ParNew",
            "PS Scavenge",
            "G1 Young Generation");

    private static final Set<String> OLD_GC_SET = SetUtils.of(
            "MarkSweepCompact",
            "PS MarkSweep",
            "ConcurrentMarkSweep",
            "G1 Old Generation");

    private static final Set<String> Z_GC_SET = SetUtils.of("ZGC");

    private static volatile long lastYoungGcTime;

    private static volatile long lastYoungGcCount;

    private static volatile long lastOldGcTime;

    private static volatile long lastOldGcCount;

    private static volatile long lastZGcTime;

    private static volatile long lastZGcCount;


    @Override
    public void run() {
        long start = System.currentTimeMillis();
        JvmGcMetrics jvmGcMetrics = collectGcMetrics();
        long end = System.currentTimeMillis();
        String format = format(jvmGcMetrics, start, end);
        Recorders.record(new Recorder(logger, false, format));

        start = System.currentTimeMillis();
        JvmMemoryMetrics jvmMemoryMetrics = collectMemoryMetrics();
        end = System.currentTimeMillis();
        format = format(jvmMemoryMetrics, start, end);
        Recorders.record(new Recorder(logger, false, format));
    }



    public static JvmMemoryMetrics collectMemoryMetrics() {
        long oldGenUsed = 0L, oldGenMax = 0L;
        long permGenUsed = 0L, permGenMax = 0L;
        long edenUsed = 0L, edenMax = 0L;
        long survivorUsed = 0L, survivorMax = 0L;
        long metaspaceUsed = 0L, metaSpaceMax = 0L;
        long codeCacheUsed = 0L, codeCacheMax = 0L;

        List<MemoryPoolMXBean> mxBeanList = ManagementFactory.getMemoryPoolMXBeans();
        for (int i = 0; i < mxBeanList.size(); i++) {
            MemoryPoolMXBean memoryPool = mxBeanList.get(i);
            MemoryUsage usage = memoryPool.getUsage();
            String poolName = memoryPool.getName();
            if (poolName.endsWith("Perm Gen")) {
                permGenUsed = usage.getUsed() >> 10;
                permGenMax = usage.getMax() >> 10;
            } else if (poolName.endsWith("Metaspace")) {
                metaspaceUsed = usage.getUsed() >> 10;
                metaSpaceMax = usage.getMax() >> 10;
            } else if (poolName.endsWith("Code Cache")) {
                codeCacheUsed = usage.getUsed() >> 10;
                codeCacheMax = usage.getMax() >> 10;
            } else if (poolName.endsWith("Old Gen")) {
                oldGenUsed = usage.getUsed() >> 10;
                oldGenMax = usage.getMax() >> 10;
            } else if (poolName.endsWith("Eden Space")) {
                edenUsed = usage.getUsed() >> 10;
                edenMax = usage.getMax() >> 10;
            } else if (poolName.endsWith("Survivor Space")) {
                survivorUsed = usage.getUsed() >> 10;
                survivorMax = usage.getMax() >> 10;
            }
        }

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage nonHeapMem = memoryMXBean.getNonHeapMemoryUsage();
        long nonHeapUsed = nonHeapMem.getUsed() >> 10;
        long nonHeapMax = nonHeapMem.getMax() >> 10;

        MemoryUsage heapMem = memoryMXBean.getHeapMemoryUsage();
        long heapUsed = heapMem.getUsed() >> 10;
        long heapMax = heapMem.getMax() >> 10;

        return new JvmMemoryMetrics(
                heapUsed, heapMax,
                nonHeapUsed, nonHeapMax,
                permGenUsed, permGenMax,
                metaspaceUsed, metaSpaceMax,
                codeCacheUsed, codeCacheMax,
                oldGenUsed, oldGenMax,
                edenUsed, edenMax,
                survivorUsed, survivorMax);
    }


    public static JvmGcMetrics collectGcMetrics() {
        long youngGcCount = 0L;
        long youngGcTime = 0L;
        long oldGcCount = 0L;
        long oldGcTime = 0L;
        long zGcCount = 0L;
        long zGcTime = 0L;

        List<GarbageCollectorMXBean> gcMXBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        for (int i = 0; i < gcMXBeanList.size(); i++) {
            GarbageCollectorMXBean gcMxBean = gcMXBeanList.get(i);
            String gcName = gcMxBean.getName();
            if (YOUNG_GC_SET.contains(gcName)) {
                youngGcTime += gcMxBean.getCollectionTime();
                youngGcCount += gcMxBean.getCollectionCount();
            } else if (OLD_GC_SET.contains(gcName)) {
                oldGcTime += gcMxBean.getCollectionTime();
                oldGcCount += gcMxBean.getCollectionCount();
            } else if (Z_GC_SET.contains(gcName)) {
                zGcTime += gcMxBean.getCollectionTime();
                zGcCount += gcMxBean.getCollectionCount();
            } else {
                Logger.warn("Unknown GC: " + gcName);
            }
        }

        JvmGcMetrics jvmGcMetrics = new JvmGcMetrics(
                youngGcCount - lastYoungGcCount,
                youngGcTime - lastYoungGcTime,
                oldGcCount - lastOldGcCount,
                oldGcTime - lastOldGcTime,
                zGcCount - lastZGcCount,
                zGcTime - lastZGcTime);

        lastYoungGcCount = youngGcCount;
        lastYoungGcTime = youngGcTime;
        lastOldGcCount = oldGcCount;
        lastOldGcTime = oldGcTime;
        lastZGcCount = zGcCount;
        lastZGcTime = zGcTime;
        return jvmGcMetrics;
    }

    public String format(JvmGcMetrics metrics, long startMillis, long stopMillis) {
        String dataTitleFormat = "%-15s%15s%15s%15s%15s%15s%15s%15s%25s%n";
        StringBuilder sb = new StringBuilder((3) * (9 * 3 + 64));
        sb.append(BannerUtils.buildBanner("MonitorJ JVM GC ", startMillis, stopMillis));
        sb.append(String.format(dataTitleFormat, "YoungGcCount", "YoungGcTime", "AvgYoungGcTime", "FullGcCount",
                "FullGcTime", "ZGcCount", "ZGcTime", "AvgZGcTime", "TotalMonitorMethods"));

        String dataFormat = "%-15s%15d%15.2f%15d%15d%15d%15d%15.2f%25s%n";
        sb.append(
                String.format(dataFormat,
                        metrics.getYoungGcCount(),
                        metrics.getYoungGcTime(),
                        metrics.getAvgYoungGcTime(),
                        metrics.getFullGcCount(),
                        metrics.getFullGcTime(),
                        metrics.getZGcCount(),
                        metrics.getZGcTime(),
                        metrics.getAvgZGcTime(),
                        MethodTagMaintainer.getInstance().getMethodTagCount()
                )
        );
        return sb.toString();
    }


    public String format(JvmMemoryMetrics metrics, long startMillis, long stopMillis) {
        String dataTitleFormat = "%-14s%21s%12s%17s%12s%19s%12s%17s%13s%19s%13s%20s%15s%22s%15s%22s%n";
        StringBuilder sb = new StringBuilder((1) * (9 * 19 + 64));
        sb.append(BannerUtils.buildBanner("MonitorJ JVM Memory ", startMillis, stopMillis));
        sb.append(String.format(dataTitleFormat,
                "SurvivorUsed", "SurvivorUsedPercent",
                "EdenUsed", "EdenUsedPercent",
                "OldGenUsed", "OldGenUsedPercent",
                "HeapUsed", "HeapUsedPercent",
                "NonHeapUsed", "NoHeapUsedPercent",
                "PermGenUsed", "PermGenUsedPercent",
                "MetaspaceUsed", "MetaspaceUsedPercent",
                "CodeCacheUsed", "CodeCacheUsedPercent"));


        String dataFormat = "%-14d%21.2f%12d%17.2f%12d%19.2f%12d%17.2f%13d%19.2f%13d%20.2f%15d%22.2f%15d%22.2f%n";
        sb.append(
                String.format(dataFormat,
                        metrics.getSurvivorUsed(),
                        metrics.getSurvivorUsedPercent(),
                        metrics.getEdenUsed(),
                        metrics.getEdenUsedPercent(),
                        metrics.getOldGenUsed(),
                        metrics.getOldGenUsedPercent(),
                        metrics.getHeapUsed(),
                        metrics.getHeapUsedPercent(),
                        metrics.getNonHeapUsed(),
                        metrics.getNonHeapUsedPercent(),
                        metrics.getPermGenUsed(),
                        metrics.getPermGenUsedPercent(),
                        metrics.getMetaspaceUsed(),
                        metrics.getMetaspaceUsedPercent(),
                        metrics.getCodeCacheUsed(),
                        metrics.getCodeCacheUsedPercent()
                )
        );
        return sb.toString();
    }
}
