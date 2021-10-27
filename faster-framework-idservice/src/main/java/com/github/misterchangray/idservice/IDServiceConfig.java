package com.github.misterchangray.idservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "com.github.misterchangray.idservice"
)
public class IDServiceConfig {
    private int appId;
    private int appInstanceId;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getAppInstanceId() {
        return appInstanceId;
    }

    public void setAppInstanceId(int appInstanceId) {
        this.appInstanceId = appInstanceId;
    }
}
