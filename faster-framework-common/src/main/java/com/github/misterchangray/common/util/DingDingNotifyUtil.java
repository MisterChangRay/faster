package com.github.misterchangray.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;

public class DingDingNotifyUtil {


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
    private static   Pattern pattern = Pattern.compile("\"");



    private static String buildSign(long timestamp, String secret) {
        String sign = "";
        try {
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

    /**
     * 钉钉机器人通知工具类
     * @param url
     * @param msg
     * @param secret
     * @return
     */
    public static String sendMsg(String url, String msg, String secret) {
        if(Objects.isNull(url)) {
            throw  new RuntimeException("invalid Url");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"msgtype\":\"text\", \"text\":{\"content\":\"");
        sb.append(msg);
        sb.append("\"}}");

        Long timestamp = System.currentTimeMillis();
        String sign = buildSign(timestamp, secret);
        String s = null;
        try {
            s = HttpClient.executePost(url + "&sign=" + sign + "&timestamp=" + timestamp, sb.toString());
        } catch (IOException e) { }
        return s;
    }
}
