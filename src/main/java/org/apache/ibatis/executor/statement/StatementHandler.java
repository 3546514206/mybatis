/*
 *    Copyright 2009-2012 the original author or authors.
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
package org.apache.ibatis.executor.statement;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * StatementHandler封装了对JDBC Statement对象的操作，
 * 比如为Statement对象设置参数，调用Statement接口提供的方法与数据库交互，
 * 设置Statement对象的fetchSize属性、设置查询超时时间、调用JDBC Statement与数据库交互等。
 *
 * @author Clinton Begin
 */
public interface StatementHandler {

    /**
     * 该方法用于创建JDBC Statement对象，并完成Statement对象的属性设置。
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    Statement prepare(Connection connection)
            throws SQLException;

    /**
     * 该方法使用MyBatis中的ParameterHandler组件为
     * PreparedStatement和CallableStatement参数占位符设置值。
     *
     * @param statement
     * @throws SQLException
     */
    void parameterize(Statement statement)
            throws SQLException;

    void batch(Statement statement)
            throws SQLException;

    int update(Statement statement)
            throws SQLException;

    <E> List<E> query(Statement statement, ResultHandler resultHandler)
            throws SQLException;

    BoundSql getBoundSql();

    ParameterHandler getParameterHandler();

}
