package com.github.misterchangray.monitor.log;

public class Recorders {
    private static int MAX_QUEUE = 1000;
    private static int batchSize = 10;
    private static Recorders ins = new Recorders();

    private  Recorder[] recorders = new Recorder[MAX_QUEUE];
    private  int read_index = 0;
    private  int write_index = 0;
    private  int capacity = 0;

    public static Recorders getInstance() {
        return ins;
    }

    public synchronized boolean record(Recorder recorder) {
        if(capacity == MAX_QUEUE) {
            return false;
        }

        if(write_index == MAX_QUEUE) {
            write_index = 0;
        }

        recorders[write_index ++] = recorder;
        capacity ++;
        return true;
    }

    public synchronized Recorder[] fetch() {
        if(capacity == 0) {
            return null;
        }

        Recorder[] recorder = new Recorder[batchSize];
        for (int i = 0; i < capacity && i < batchSize; i++) {
            if(read_index == MAX_QUEUE) {
                read_index = 0;
            }

            recorder[i] = recorders[read_index];
            recorders[read_index ++] = null;
            capacity --;
        }

        return recorder;
    }
}
