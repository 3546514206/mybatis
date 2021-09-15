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
package org.apache.ibatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用JDBC API开发应用程序，其中一个比较烦琐的环节是处理JDBC类型与Java类型之间的转换。
 * 涉及Java类型和JDBC类型转换的两种情况如下：
 * （1）PreparedStatement对象为参数占位符设置值时，需要调用PreparedStatement接口中提供
 * 的一系列的setXXX()方法，将Java类型转换为对应的JDBC类型并为参数占位符赋值。
 * （2）执行SQL语句获取ResultSet对象后，需要调用ResultSet对象的getXXX()方法获取字段值，此
 * 时会将JDBC类型转换为Java类型。
 *
 * MyBatis中使用TypeHandler解决上面两种情况下，JDBC类型与Java类型之间的转换。
 *
 * TypeHandler是MyBatis中的类型处理器，用于处理Java类型与JDBC类型之
 * 间的映射。它的作用主要体现在能够
 * 根据Java类型调用PreparedStatement或CallableStatement对象对
 * 应的setXXX()方法为Statement对象设置值，而且能够根据Java类型调
 * 用ResultSet对象对应的getXXX()获取SQL执行结果。
 *
 * @author Clinton Begin
 */
public interface TypeHandler<T> {

    /**
     * setParameter()方法用于为PreparedStatement对象参数的占位符设置值
     *
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

    /**
     * 另外3个重载的getResult()方法用于从ResultSet对象中获取列的值，或者获取存储过程调用结果。
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    T getResult(ResultSet rs, String columnName) throws SQLException;

    T getResult(ResultSet rs, int columnIndex) throws SQLException;

    T getResult(CallableStatement cs, int columnIndex) throws SQLException;

}
