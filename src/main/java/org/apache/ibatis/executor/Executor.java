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
package org.apache.ibatis.executor;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

/**
 * SqlSession是MyBatis提供的操作数据库的API，但是真正执行SQL的
 * 是Executor组件。Executor是MyBatis的SQL执行器，MyBatis中对数据库所有的
 * 增删改查操作都是由Executor组件完成的。Executor接口中定义了对数据库的增删改查方法，其
 * 中query()和queryCursor()方法用于执行查询操作，update()方法用
 * 于执行插入、删除、修改操作。
 * <p>
 *                      Executor接口
 *                     /\          /\
 *                     |           |
 *                     |           |
 *           BaseExecutor抽象类   CachingExecutor
 *           /\    /\   /\
 *           |     |    |
 *SimpleExecutor   |    |
 *       ReuseExecutor  |
 *              BatchExecutor
 * <p>
 * <p>
 * MyBatis提供了3种不同的Executor，分别为SimpleExecutor、ResueExecutor、BatchExecutor。
 * 这些Executor都继承至BaseExecutor，BaseExecutor实现了Executor，BaseExecutor中定义了方法的执行
 * 流程及通用的处理逻辑，具体的方法由子类来实现，是典型的模板方法模式的应用。
 * 1) SimpleExecutor是基础的Executor，能够完成基本的增删改查操作。
 * 2) ReuseExecutor对JDBC中的Statement对象做了缓存，当执行相同的SQL语句时，
 * 直接从缓存中取出Statement对象进行复用，避免了频繁创建和销毁Statement对象，
 * 从而提升系统性能，这是享元思想的应用。
 * 3) BatchExecutor则会对调用同一个Mapper执行的update、insert和delete操作，调用Statement对象的批量操作功能。
 * 4) 另外，我们知道MyBatis支持一级缓存和二级缓存，当MyBatis开启了二级缓存功能时，
 * 会使用CachingExecutor对SimpleExecutor、ResueExecutor、BatchExecutor进行装饰，
 * 为查询操作增加二级缓存功能，这是装饰器模式的应用。
 *
 * @author Clinton Begin
 */
public interface Executor {

    ResultHandler NO_RESULT_HANDLER = null;

    int update(MappedStatement ms, Object parameter) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

    List<BatchResult> flushStatements() throws SQLException;

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

    boolean isCached(MappedStatement ms, CacheKey key);

    void clearLocalCache();

    void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

    Transaction getTransaction();

    void close(boolean forceRollback);

    boolean isClosed();

    void setExecutorWrapper(Executor executor);

}
