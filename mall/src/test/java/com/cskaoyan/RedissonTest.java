package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @date 2022/07/19 23:14
 * @author fanxing056
 */
@SpringJUnitConfig
@SpringBootTest
public class RedissonTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testRedisson() {

        RBucket<Object> mykey = redissonClient.getBucket("mykey");
        mykey.set("7980", 100000, TimeUnit.MILLISECONDS);
        System.out.println(mykey.get());
    }
}
