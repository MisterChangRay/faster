package com.github.misterchangray.monitor.notifys;

import com.github.misterchangray.common.util.DingDingNotifyUtil;
import com.github.misterchangray.common.util.EscapeUtils;
import com.github.misterchangray.monitor.Notify;
import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.utils.Logger;

import java.util.Objects;

public class DingDingNotify implements Notify {

    @Override
    public void init() {

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

        String s = DingDingNotifyUtil.sendMsg(customConfig.getNotifyUrlOfDingDing(), EscapeUtils.escapeJson(recorder.toString()),
                customConfig.getNotifySecretOfDingDing()) ;
        Logger.debug("DingDing Http Send Result -> " + s);

    }

}
