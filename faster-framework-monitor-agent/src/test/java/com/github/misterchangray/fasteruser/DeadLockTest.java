package com.github.misterchangray.fasteruser;

import com.github.misterchangray.monitor.JSystem;
import org.junit.Test;

import java.util.Date;

public class DeadLockTest {
    @Test
    public void test7() {
        LockA la = new LockA();
        new Thread(la).start();
        LockB lb = new LockB();
        new Thread(lb).start();

        for (int i = 0; i < 10000; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            StringBuilder monitorDeadlockedThreads = JSystem.findMonitorDeadlockedThreads();

            if(null != monitorDeadlockedThreads) {
                System.out.println(monitorDeadlockedThreads.toString());
                new QueueTest().dosendmsg(monitorDeadlockedThreads.toString());
            }
        }

    }

    class LockA implements Runnable{
        public void run() {
            try {
                System.out.println(new Date().toString() + " LockA 开始执行");
                while(true){
                    synchronized (LockA.class) {
                        System.out.println(new Date().toString() + " LockA 锁住 obj1");
                        Thread.sleep(3000); // 此处等待是给B能锁住机会
                        synchronized (LockB.class) {
                            System.out.println(new Date().toString() + " LockA 锁住 obj2");
                            Thread.sleep(60 * 1000); // 为测试，占用了就不放
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    class LockB implements Runnable{
        public void run() {
            try {
                System.out.println(new Date().toString() + " LockB 开始执行");
                while(true){
                    synchronized (LockB.class) {
                        System.out.println(new Date().toString() + " LockB 锁住 obj2");
                        Thread.sleep(3000); // 此处等待是给A能锁住机会
                        synchronized (LockA.class) {
                            System.out.println(new Date().toString() + " LockB 锁住 obj1");
                            Thread.sleep(60 * 1000); // 为测试，占用了就不放
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
