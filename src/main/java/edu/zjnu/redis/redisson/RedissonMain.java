package edu.zjnu.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @description: RedissonMain
 * @author: 杨海波
 * @date: 2021-10-13
 **/
public class RedissonMain {

    public static void main(String[] args) {
        RedissonClient client = getRedissonClient();

        RBucket<String> bucket = client.getBucket("name");
        bucket.set("zhaoyun");

        RLock lock = client.getLock("yhb");
        lock.lock();
        lock.unlock();
        client.shutdown();
    }

    private static RedissonClient getRedissonClient() {

        Config config = new Config();
        //config.setCodec(new org.redisson.client.codec.StringCodec());
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }
}
