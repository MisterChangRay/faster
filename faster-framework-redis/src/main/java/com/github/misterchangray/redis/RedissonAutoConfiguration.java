package com.github.misterchangray.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties({RedisConfig.class})
public class RedissonAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Bean
    public RedissonClient redissonClient(RedisConfig redisConfig) {
        Config config = new Config();
        if(Objects.nonNull(redisConfig.getSingle())) {
            String address = String.format("redis://%s:%s",
                    redisConfig.getSingle().getHost(), redisConfig.getSingle().getPort());
            logger.info("RedissonClient 初始化成功! 初始化连接信息: {}, pwd: {}, db: {}", address,
                    redisConfig.getSingle().getPassword(), redisConfig.getSingle().getDb());
            config.useSingleServer()
                    .setPassword(redisConfig.getSingle().getPassword())
                    .setDatabase(redisConfig.getSingle().getDb())
                    .setAddress(address)
                    .setConnectTimeout(12000)
                    .setTimeout(100)
                    .setConnectionPoolSize(100);
        } else {
            logger.error("RedissonClient 初始化失败, 未发现可用配置初始化, 请参考文档进行配置!");
        }

        return Redisson.create(config);
    }


}
