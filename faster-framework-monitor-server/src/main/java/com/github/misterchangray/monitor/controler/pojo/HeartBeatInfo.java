package com.github.misterchangray.monitor.controler.pojo;

public class HeartBeatInfo {
    private HeartBeat heartBeat;
    private long lastHeartBeatTime;
    private long heartBeatTime;


    public long getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    public void setLastHeartBeatTime(long lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

    public HeartBeat getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(HeartBeat heartBeat) {
        this.heartBeat = heartBeat;
    }

    public long getHeartBeatTime() {
        return heartBeatTime;
    }

    public void setHeartBeatTime(long heartBeatTime) {
        this.heartBeatTime = heartBeatTime;
    }


}
