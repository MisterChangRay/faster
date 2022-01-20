package com.github.misterchangray.fasteruser;

import com.github.misterchangray.monitor.CpuMonitor;
import com.github.misterchangray.monitor.JSystem;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.consts.Consts;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.notifys.DingDingNotify;
import com.github.misterchangray.monitor.utils.DateFormatUtils;
import com.github.misterchangray.monitor.utils.HttpClient;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class QueueTest {
    static List<List> a = new ArrayList<>();

    @Test
    public void test7() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        System.out.println(11);
    }

    @Test
    public void sendmsg( ) {
        new QueueTest().dosendmsg("asdfsf");
    }

    public void dosendmsg( String msg) {
        msg = msg.replaceAll("\"", "\\\\\"");
        String m = "{\"msgtype\":\"text\", \"text\":{\"content\":\"" + msg +"\"}}";
        Long timestamp = System.currentTimeMillis();

        String sign = "";
        try {
            String secret = "SEC028a906e7ca41b79f2b91503793025c7b53aaf0ad3fbef0974bc8fc0d4b16a33";

            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            sign = URLEncoder.encode(Base64.getEncoder().encodeToString(signData),"UTF-8");
            System.out.println(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = HttpClient.executePost("https://oapi.dingtalk.com/robot/send?access_token=7f2f1abba57510f0538832948844758c8ba4a58e68fc6b28bb26d972dd0ae1b2&sign=" + sign + "&timestamp=" + timestamp ,
                m);
        System.out.println(m);
        System.out.println(s);
    }

    @Test
    public void test5() {
        System.out.println(System.getProperty("-Xmx"));
        System.out.println(Runtime.getRuntime().maxMemory());
    }

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
