package com.github.misterchangray.fasteruser;

import com.github.misterchangray.monitor.config.CustomConfig;
import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.log.Recorders;
import com.github.misterchangray.monitor.notifys.DingDingNotify;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DingDingNotifyTest {
    static List<List> a = new ArrayList<>();

    @Before
    public void initProperties () {
        CustomConfig m = new CustomConfig();
        m.setNotifySecretOfDingDing("SEC028a906e7ca41b79f2b91503793025c7b53aaf0ad3fbef0974bc8fc0d4b16a33");
        m.setNotifyUrlOfDingDing("https://oapi.dingtalk.com/robot/send?access_token=7f2f1abba57510f0538832948844758c8ba4a58e68fc6b28bb26d972dd0ae1b2");
        ProfilingConfig.setCustomConfig(m);
    }

    @Test
    public void testSend() {
        DingDingNotify dingDingNotify = new DingDingNotify();
        dingDingNotify.notify(new StringBuilder("asdfasfsaf"));
    }
}
