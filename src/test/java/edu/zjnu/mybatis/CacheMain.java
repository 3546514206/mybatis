package edu.zjnu.mybatis;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.*;
import org.apache.ibatis.cache.impl.PerpetualCache;

import java.io.IOException;

/**
 * @description: 二级缓存测试
 * @author: 杨海波
 * @date: 2021-09-21
 **/
public class CacheMain {

    public static void main(String[] args) throws IOException {
//        Reader reader = Resources.getResourceAsReader("configuration-cache.xml");
//        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
//        SqlSession session = factory.openSession();
//
//        List<User> users = session.selectList("edu.zjnu.mybatis.dao.UserDao.getUsersByCache");
//
//        users.forEach(e -> System.out.println(e.getUsername()));
//        session.close();
//        reader.close();

        final int N = 100000;
        Cache cache = new PerpetualCache("default");
        cache = new LruCache(cache);
        cache = new FifoCache(cache);
        cache = new SoftCache(cache);
        cache = new WeakCache(cache);
        cache = new ScheduledCache(cache);
        cache = new SerializedCache(cache);
        cache = new SynchronizedCache(cache);
        cache = new TransactionalCache(cache);
        for (int i = 0; i < N; i++) {
            cache.putObject(i, i);
            ((TransactionalCache) cache).commit();
        }

        System.out.println(cache.getSize());
    }
}
