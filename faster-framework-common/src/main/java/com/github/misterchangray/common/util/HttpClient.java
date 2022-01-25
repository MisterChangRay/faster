package com.github.misterchangray.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    public static String executePost(String targetURL, String params) throws IOException {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",  "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(params.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(params);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
