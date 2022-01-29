package com.github.misterchangray.fasteruser;

import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QueueTest {
    static List<List> a = new ArrayList<>();


    @Test
    public void testQueue() {

        for (int k = 0; k < 3; k++) {
            new Thread(() -> {
                long i =0;
                while (true) {
                    Recorder[] recorders = Recorders.getInstance().fetch();
                    if(null == recorders) continue;
                    for (Recorder recorder : recorders) {
                        if(null == recorder) {
                            continue;
                        }

                        System.out.println(recorder.getMsg());
                    }

                }
            }).start();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                long j =0;
//                for (int k = 0; k < 10000; k++) {
//                    j ++;
//                    Recorders.getInstance().record(new Recorder(null, false,Thread.currentThread().getId()+ "- " + j));
//
//                }

                for (int k = 0; k < 10; k++) {
                    j ++;
                    Recorders.getInstance().record(new Recorder(null, false,Thread.currentThread().getId()+ "- " + j));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                for (int k = 0; k < 2; k++) {
                    j ++;
                    Recorders.getInstance().record(new Recorder(null, false,Thread.currentThread().getId()+ "- " + j));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }





        try {
            Thread.sleep(40000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
