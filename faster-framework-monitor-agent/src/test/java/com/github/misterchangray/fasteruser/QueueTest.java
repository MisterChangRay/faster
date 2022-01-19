package com.github.misterchangray.fasteruser;

import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;

public class QueueTest {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                long j =0;
                while (true) {
                    j ++;
                    Recorders.record(new Recorder(null, Thread.currentThread().getId()+ "- " + j));
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
