package edu.zjnu.redis.jediss;

import redis.clients.jedis.Jedis;

/**
 * @description: JedissMain
 * @author: 杨海波
 * @date: 2021-10-15
 **/
public class JedissMain {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("localhost", 6379);  //指定Redis服务Host和port
        //jedis.auth("xxxx"); //如果Redis服务连接需要密码，制定密码
        String rs = jedis.set("key", "value"); //访问Redis服务
        System.out.println(rs);

        String value = jedis.get("key");
        System.out.println(value);
        jedis.close(); //使用完关闭连接
    }
}
