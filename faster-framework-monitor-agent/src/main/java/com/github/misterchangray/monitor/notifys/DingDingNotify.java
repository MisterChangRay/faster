package com.github.misterchangray.monitor.notifys;

import com.github.misterchangray.monitor.Notify;
import com.github.misterchangray.monitor.config.MonitorConfig;
import com.github.misterchangray.monitor.config.ProfilingConfig;
import com.github.misterchangray.monitor.log.Recorder;
import com.github.misterchangray.monitor.utils.HttpClient;
import com.github.misterchangray.monitor.utils.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;

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
    private  Pattern pattern = Pattern.compile("\"");
    @Override
    public void notify(Recorder recorder) {
        this.notify(new StringBuilder(recorder.toString()));
    }


    private static String buildSign(long timestamp, MonitorConfig monitorConfig) {
        String sign = "";
        try {
            String secret = monitorConfig.getNotifySecretOfDingDing();

            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            sign = URLEncoder.encode(Base64.getEncoder().encodeToString(signData),"UTF-8");
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void notify(StringBuilder recorder) {
        MonitorConfig monitorConfig = ProfilingConfig.getMonitorConfig();
        if(Objects.isNull(monitorConfig.getNotifyUrlOfDingDing())) return;

        StringBuilder sb = new StringBuilder();
        sb.append("{\"msgtype\":\"text\", \"text\":{\"content\":\"");
        sb.append(pattern.matcher(recorder.toString()).replaceAll(""));
        sb.append("\"}}");

        Long timestamp = System.currentTimeMillis();
        String sign = buildSign(timestamp,monitorConfig);
        String s = HttpClient.executePost(monitorConfig.getNotifyUrlOfDingDing() + "&sign=" + sign + "&timestamp=" + timestamp, sb.toString());
        Logger.debug("DingDing Http Send Result -> " + s);

    }
}
