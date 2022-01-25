package com.github.misterchangray.monitor.controler;

import com.github.misterchangray.common.consts.SystemConst;
import com.github.misterchangray.common.util.DateFormatUtils;
import com.github.misterchangray.common.util.DingDingNotifyUtil;
import com.github.misterchangray.common.util.EscapeUtils;
import com.github.misterchangray.common.util.SchedulerExecutor;
import com.github.misterchangray.monitor.controler.pojo.HeartBeat;
import com.github.misterchangray.monitor.controler.pojo.HeartBeatInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RestController
public class MonitorController {
    private static ConcurrentHashMap<String, HeartBeatInfo> cache = new ConcurrentHashMap();
    Thread monitor = null;

    @RequestMapping("/heartbeat")
    public void heartbeat(@RequestBody HeartBeat heartBeat) {

        HeartBeatInfo heartBeatInfo = cache.get(heartBeat.getAppName());
        if(null == cache.get(heartBeat.getAppName())) {
            heartBeatInfo = new HeartBeatInfo();
            heartBeatInfo.setLastHeartBeatTime(System.currentTimeMillis());
            cache.put(heartBeat.getAppName(), heartBeatInfo);

        } else {
            heartBeatInfo.setLastHeartBeatTime(heartBeatInfo.getHeartBeatTime());
            heartBeatInfo.setHeartBeatTime(System.currentTimeMillis());
        }

        heartBeatInfo.setHeartBeat(heartBeat);
        SchedulerExecutor.execOnce(new MonitorTask(heartBeatInfo), 23);
    }

    private static int secOf20   =  20 * 1000;
    class MonitorTask implements Runnable {
        private HeartBeatInfo info;
        public MonitorTask(HeartBeatInfo heartBeatInfo) {
                      this.info= heartBeatInfo;
        }

        @Override
        public void run() {
            try {
                System.out.println("开始执行" + info.getHeartBeat().getAppName());
                HeartBeatInfo heartBeatInfo = cache.get(info.getHeartBeat().getAppName());
                if(null == heartBeatInfo) return;


                long ttl = System.currentTimeMillis() - heartBeatInfo.getLastHeartBeatTime();
                if(ttl < 0 || ttl < secOf20) {
                    return;
                }

                HeartBeat heartBeat = heartBeatInfo.getHeartBeat();
                StringBuilder sb = buildBanner(heartBeat);
                sb.append("application has ben offline !!! path:" + EscapeUtils.escapeJson( heartBeat.getProjectPath()));
                // send message
                String url = heartBeat.getDingdingUrl();
                String sec = heartBeat.getDingdingSec();
                DingDingNotifyUtil.sendMsg(url, sb.toString(), sec);

                cache.remove(heartBeatInfo.getHeartBeat().getAppName());
            } catch (Exception ae) {
            }
        }
    }

    public static StringBuilder buildBanner(HeartBeat heartBeat) {
        StringBuilder sb = new StringBuilder();
        sb.append("MonitorJ AppMonitor");
        sb.append("[");
        sb.append(EscapeUtils.escapeJson(  heartBeat.getAppName()));
        sb.append("][");
        sb.append(heartBeat.getProcessId());
        sb.append("][");
        sb.append(DateFormatUtils.format(System.currentTimeMillis())).append(']');

        sb .append(SystemConst.LINE_SEPARATOR);
        return sb;
    }

}
