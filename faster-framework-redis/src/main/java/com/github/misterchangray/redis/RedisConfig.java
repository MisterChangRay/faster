package com.github.misterchangray.redis;

import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "com.github.misterchangray.redis"
)
public class RedisConfig extends Config {
    private Single single;

    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }

    static class Single {
        private String host;
        private String port;
        private int db;
        private String password;



        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public int getDb() {
            return db;
        }

        public void setDb(int db) {
            this.db = db;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
