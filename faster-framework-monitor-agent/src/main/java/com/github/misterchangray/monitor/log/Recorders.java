package com.github.misterchangray.monitor.log;

public class Recorders {
    private static int MAX_QUEUE = 1000;
    private static Recorder[] recorders = new Recorder[MAX_QUEUE];
    private static int read_index = 0;
    private static int write_index = 0;
    private static int capacity = 0;

    public static synchronized boolean record(Recorder recorder) {
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

    public static synchronized Recorder fetch() {
        if(capacity == 0) {
            return  null;
        }

        if(read_index == MAX_QUEUE) {
            read_index = 0;
        }

        Recorder recorder = recorders[read_index];
        recorders[read_index++] = null;
        capacity --;
        return recorder;
    }
}
