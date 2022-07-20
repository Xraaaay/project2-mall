package com.cskaoyan.config.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置类
 *
 * @author fanxing056
 * @date 2022/07/20 19:48
 */

@Configuration
public class RedissonConfig {

    @Value("${redis.redisson.url}")
    String url;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(url);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
