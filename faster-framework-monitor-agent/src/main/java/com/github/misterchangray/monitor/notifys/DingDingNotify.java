package com.github.misterchangray.monitor.notifys;

import com.github.misterchangray.common.consts.SystemConst;
import com.github.misterchangray.common.util.DingDingNotifyUtil;
import com.github.misterchangray.common.util.EscapeUtils;
import com.github.misterchangray.common.util.SchedulerExecutor;
import com.github.misterchangray.monitor.Notify;
import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.utils.Logger;

import java.util.Objects;

public class DingDingNotify implements Notify {
    private StringBuilder cache = new StringBuilder();
    private static int kb10 = 10 * 1024;

    @Override
    public void init() {
        SchedulerExecutor.execWithFixedDelay(() -> {
            synchronized (DingDingNotify.class) {
                if(cache.length() > 0) {
                    doNotify();
                    cache = cache.delete(0, cache.length());
                }
            }
        }, 3);
    }

    /**
     * {
     *
     *
     *
     *    'msgtype': 'text',
     *
     *
     *    'text':'{}'
     *
     *
     * }
     * @param recorder
     */
    @Override
    public void notify(Recorder recorder) {
        this.notify(new StringBuilder(recorder.getMsg()));
    }


    @Override
    public void notify(StringBuilder recorder) {
        CustomConfig customConfig =  ProfilingConfig.getCustomConfig();
        if(Objects.isNull(customConfig.getNotifyUrlOfDingDing())) return;

        synchronized (DingDingNotify.class) {
            if(cache.length() > kb10) {
              return;
            }

            cache.append(recorder);
        }
    }


    private void doNotify() {
        try {
            CustomConfig customConfig =  ProfilingConfig.getCustomConfig();

            String s = DingDingNotifyUtil.sendMsg(customConfig.getNotifyUrlOfDingDing(), EscapeUtils.escapeJson(cache.toString()),
                    customConfig.getNotifySecretOfDingDing()) ;
            Logger.debug("DingDing Http Send Result -> " + s);
        } catch (Exception ae) {}
    }
}
