/*
 *    Copyright 2009-2011 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache;

import org.apache.ibatis.cache.decorators.TransactionalCache;

import java.util.HashMap;
import java.util.Map;

/**
 * 在TransactionalCacheManager类中，通过一个HashMap对象维护所有二级缓存实例对应的TransactionalCache对象，
 * 在TransactionalCacheManager类的getObject()方法和putObject()方法中都会调用getTransactionalCache()方法
 * 获取二级缓存对象对应的TransactionalCache对象，然后对TransactionalCache对象进行操作。在getTransactionalCache()方法中，
 * 首先从HashMap对象中获取二级缓存对象对应的TransactionalCache对象，如果获取不到，
 * 则创建新的TransactionalCache对象添加到HashMap对象中。
 *
 * @author Clinton Begin
 */
public class TransactionalCacheManager {

    /**
     * 通过HashMap对象维护二级缓存对应的TransactionalCache实例
     */
    private Map<Cache, TransactionalCache> transactionalCaches = new HashMap<Cache, TransactionalCache>();

    public void clear(Cache cache) {
        getTransactionalCache(cache).clear();
    }

    public Object getObject(Cache cache, CacheKey key) {
        //获取二级缓存对应的TransactionalCache对象，并根据key获取缓存
        return getTransactionalCache(cache).getObject(key);
    }

    public void putObject(Cache cache, CacheKey key, Object value) {
        getTransactionalCache(cache).putObject(key, value);
    }

    public void commit() {
        for (TransactionalCache txCache : transactionalCaches.values()) {
            txCache.commit();
        }
    }

    public void rollback() {
        for (TransactionalCache txCache : transactionalCaches.values()) {
            txCache.rollback();
        }
    }

    private TransactionalCache getTransactionalCache(Cache cache) {
        //获取二级缓存对应的Transactional对象
        TransactionalCache txCache = transactionalCaches.get(cache);
        if (txCache == null) {
            //如果取不到则创建并放到map中
            txCache = new TransactionalCache(cache);
            transactionalCaches.put(cache, txCache);
        }

        return txCache;
    }

}
