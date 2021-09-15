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
package org.apache.ibatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 当使用PreparedStatement或者CallableStatement对象时，如果SQL语句中有参数占位符，
 * 在执行SQL语句之前，就需要为参数占位符设置值。ParameterHandler的作用是在
 * PreparedStatementHandler和CallableStatementHandler操作对应的Statement执行数据
 * 库交互之前为参数占位符设置值。
 *
 * <p>
 * A parameter handler sets the parameters of the {@code PreparedStatement}
 *
 * @author Clinton Begin
 */
public interface ParameterHandler {

    /**
     * 该方法用于获取执行Mapper时传入的参数对象。
     * @return
     */
    Object getParameterObject();

    /**
     * 该方法用于为JDBC PreparedStatement或者CallableStatement对象设置参数值。
     * @param ps
     * @throws SQLException
     */
    void setParameters(PreparedStatement ps)
            throws SQLException;

}
