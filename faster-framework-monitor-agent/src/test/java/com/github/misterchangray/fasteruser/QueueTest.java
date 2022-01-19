package com.github.misterchangray.fasteruser;

import com.github.misterchangray.monitor.CpuMonitor;
import com.github.misterchangray.monitor.JSystem;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.DateFormatUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QueueTest {
    static List<List> a = new ArrayList<>();
    @Test
    public void test4() {
        while (true) {
            long noneHeapMemoryUsage = JSystem.getNoneHeapMemoryUsage();
            System.out.println(noneHeapMemoryUsage);
            System.out.println(JSystem.getHeapMemoryUsage());

            for (int i = 0; i < 1000; i++) {
                ArrayList b = new ArrayList();
                b.add("c");
                a.add(b);
            }
            sleep(10);
        }

    }


    @Test
    public void test() {
        new Thread(() -> {
            while (true) {
                double cpuUsed = JSystem.getCpuUsed() * 100;

                long stopMillis = System.currentTimeMillis();
                if(cpuUsed > 50) {
                    StringBuilder sb = new StringBuilder(256);
                    sb.append("MonitorJ CPU [").append(DateFormatUtils.format(System.currentTimeMillis())).append(", ")
                            .append(DateFormatUtils.format(stopMillis)).append(']').append(Consts.LINE_SEPARATOR);

                    String format = String.format("application: %s, Pid: %s, cpuUsage: %s %% !",
                           "",
                            "",
                            (int) cpuUsed);

                    sb.append(format);
                    System.out.println(sb.toString());

                }


                sleep(1000);


            }

        }).start();


        for (int i = 0; i < 40; i++) {
            new Thread(() -> {
                while (true) {

                }

            }).start();

        }
        sleep(40000);

    }

    public static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueue() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                long j =0;
                while (true) {
                    j ++;
                    Recorders.record(new Recorder(null, false,Thread.currentThread().getId()+ "- " + j));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }



        new Thread(() -> {
            long i =0;
            while (true) {
                Recorder fetch = Recorders.fetch();
                if(fetch == null) {
//                    System.out.println("拿完了" );
                } else {
                    System.out.println(fetch.getMsg());
                }
            }
        }).start();

        try {
            Thread.sleep(40000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
