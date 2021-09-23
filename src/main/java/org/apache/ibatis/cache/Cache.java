/*
 *    Copyright 2009-2014 the original author or authors.
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

import java.util.concurrent.locks.ReadWriteLock;

/**
 * SPI for cache providers.
 * <p>
 * One instance of cache will be created for each namespace.
 * <p>
 * The cache implementation must have a constructor that receives the cache id as an String parameter.
 * <p>
 * MyBatis will pass the namespace as id to the constructor.
 *
 * <pre>
 * public MyCache(final String id) {
 *  if (id == null) {
 *    throw new IllegalArgumentException("Cache instances require an ID");
 *  }
 *  this.id = id;
 *  initialize();
 * }
 * </pre>
 *
 * @author Clinton Begin
 */
// mybatis的缓存都是内存级别的缓存
public interface Cache {

    /**
     *
     * 用于获取缓存的ID，通常就是Mapper的命名空间
     * @return The identifier of this cache
     */
    String getId();

    /**
     * giant方法将一个Java对象添加到缓存中，该方法有两个参数，第一个参数为缓存的key,即CacheKey的实例
     * 第二个参数为需要缓存的对象
     *
     * @param key   Can be any object but usually it is a {@link CacheKey}
     * @param value The result of a select.
     */
    void putObject(Object key, Object value);

    /**
     * 用于从缓存中获取缓存的对象
     * @param key The key
     * @return The object stored in the cache.
     */
    Object getObject(Object key);

    /**
     *
     * 讲一个对象从缓存中移除
     * Optional. It is not called by the core.
     *
     * @param key The key
     * @return The object that was removed
     */
    Object removeObject(Object key);

    /**
     *
     * 清空缓存
     * Clears this cache instance
     */
    void clear();

    /**
     * Optional. This method is not called by the core.
     *
     * @return The number of elements stored in the cache (not its capacity).
     */
    int getSize();

    /**
     *
     * Optional. As of 3.2.6 this method is no longer called by the core.
     * <p>
     * Any locking needed by the cache must be provided internally by the cache provider.
     *
     * @return A ReadWriteLock
     */
    ReadWriteLock getReadWriteLock();

}