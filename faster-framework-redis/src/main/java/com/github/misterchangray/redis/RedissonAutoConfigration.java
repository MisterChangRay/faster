package com.github.misterchangray.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonAutoConfigration {



    @Bean
    public RedissonClient redissonClient(RedisSingleConfig redisConfig) {
        Config config = new Config();
        String address = String.format("redis://%s:%s", redisConfig.getHost(), redisConfig.getPort());
        config.useSingleServer()
                .setPassword(redisConfig.getPassword())
                .setDatabase(redisConfig.getDb())
                .setAddress(address)
                .setConnectTimeout(6000)
                .setTimeout(100)
                .setConnectionPoolSize(100);

        return Redisson.create(config);
    }


}
