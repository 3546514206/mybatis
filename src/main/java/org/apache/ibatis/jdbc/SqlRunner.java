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
package org.apache.ibatis.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * @author Clinton Begin
 */
public class SqlRunner {

    public static final int NO_GENERATED_KEY = Integer.MIN_VALUE + 1001;

    private Connection connection;
    private TypeHandlerRegistry typeHandlerRegistry;
    private boolean useGeneratedKeySupport;

    public SqlRunner(Connection connection) {
        this.connection = connection;
        this.typeHandlerRegistry = new TypeHandlerRegistry();
    }

    public void setUseGeneratedKeySupport(boolean useGeneratedKeySupport) {
        this.useGeneratedKeySupport = useGeneratedKeySupport;
    }

    /*
     * Executes a SELECT statement that returns one row.
     *
     * @param sql  The SQL
     * @param args The arguments to be set on the statement.
     * @return The number of rows impacted or BATCHED_RESULTS if the statements are being batched.
     * @throws SQLException If more than one row is returned
     */
    public Map<String, Object> selectOne(String sql, Object... args) throws SQLException {
        List<Map<String, Object>> results = selectAll(sql, args);
        if (results.size() != 1) {
            throw new SQLException("Statement returned " + results.size() + " results where exactly one (1) was expected.");
        }
        return results.get(0);
    }

    /*
     * Executes a SELECT statement that returns multiple rows.
     *
     * @param sql  The SQL
     * @param args The arguments to be set on the statement.
     * @return The number of rows impacted or BATCHED_RESULTS if the statements are being batched.
     * @throws SQLException If statement prepration or execution fails
     */
    public List<Map<String, Object>> selectAll(String sql, Object... args) throws SQLException {
        // 调用connection对象的 prepareStatement()方法获取PreparedStatement对象
        PreparedStatement ps = connection.prepareStatement(sql);
        try {
            // 构造参数，对占位符替换赋值
            setParameters(ps, args);
            // 调用prepareStatement 的 executeQuery()方法执行查询操作，并返回ResultSet
            ResultSet rs = ps.executeQuery();
            // 调用getResults()方法，将ResultSet转换为List对象，其中list对象中
            // 的每个Map对象对应数据库中的一条记录
            return getResults(rs);
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                //ignore
            }
        }
    }

    /*
     * Executes an INSERT statement.
     *
     * @param sql  The SQL
     * @param args The arguments to be set on the statement.
     * @return The number of rows impacted or BATCHED_RESULTS if the statements are being batched.
     * @throws SQLException If statement prepration or execution fails
     */
    public int insert(String sql, Object... args) throws SQLException {
        PreparedStatement ps;
        if (useGeneratedKeySupport) {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            ps = connection.prepareStatement(sql);
        }

        try {
            setParameters(ps, args);
            ps.executeUpdate();
            if (useGeneratedKeySupport) {
                List<Map<String, Object>> keys = getResults(ps.getGeneratedKeys());
                if (keys.size() == 1) {
                    Map<String, Object> key = keys.get(0);
                    Iterator<Object> i = key.values().iterator();
                    if (i.hasNext()) {
                        Object genkey = i.next();
                        if (genkey != null) {
                            try {
                                return Integer.parseInt(genkey.toString());
                            } catch (NumberFormatException e) {
                                //ignore, no numeric key suppot
                            }
                        }
                    }
                }
            }
            return NO_GENERATED_KEY;
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                //ignore
            }
        }
    }

    /*
     * Executes an UPDATE statement.
     *
     * @param sql  The SQL
     * @param args The arguments to be set on the statement.
     * @return The number of rows impacted or BATCHED_RESULTS if the statements are being batched.
     * @throws SQLException If statement prepration or execution fails
     */
    public int update(String sql, Object... args) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        try {
            setParameters(ps, args);
            return ps.executeUpdate();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                //ignore
            }
        }
    }

    /*
     * Executes a DELETE statement.
     *
     * @param sql  The SQL
     * @param args The arguments to be set on the statement.
     * @return The number of rows impacted or BATCHED_RESULTS if the statements are being batched.
     * @throws SQLException If statement prepration or execution fails
     */
    public int delete(String sql, Object... args) throws SQLException {
        return update(sql, args);
    }

    /*
     * Executes any string as a JDBC Statement.
     * Good for DDL
     *
     * @param sql The SQL
     * @throws SQLException If statement prepration or execution fails
     */
    public void run(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            stmt.execute(sql);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                //ignore
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            //ignore
        }
    }

    private void setParameters(PreparedStatement ps, Object... args) throws SQLException {
        for (int i = 0, n = args.length; i < n; i++) {
            if (args[i] == null) {
                throw new SQLException("SqlRunner requires an instance of Null to represent typed null values for JDBC compatibility");
            } else if (args[i] instanceof Null) {
                ((Null) args[i]).getTypeHandler().setParameter(ps, i + 1, null, ((Null) args[i]).getJdbcType());
            } else {
                TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(args[i].getClass());
                if (typeHandler == null) {
                    throw new SQLException("SqlRunner could not find a TypeHandler instance for " + args[i].getClass());
                } else {
                    typeHandler.setParameter(ps, i + 1, args[i], null);
                }
            }
        }
    }

    private List<Map<String, Object>> getResults(ResultSet rs) throws SQLException {
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            List<String> columns = new ArrayList<String>();
            List<TypeHandler<?>> typeHandlers = new ArrayList<TypeHandler<?>>();
            // 根据ResultSetMetaData对象获取所有列名
            ResultSetMetaData rsmd = rs.getMetaData();
            //这个循环初始化了保存列信息和对应typeHandler的线性表
            for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
                columns.add(rsmd.getColumnLabel(i + 1));
                try {
                    //根据获取的列信息的JDBC类型，获取typeHandler对象
                    Class<?> type = Resources.classForName(rsmd.getColumnClassName(i + 1));
                    TypeHandler<?> typeHandler = typeHandlerRegistry.getTypeHandler(type);
                    if (typeHandler == null) {
                        typeHandler = typeHandlerRegistry.getTypeHandler(Object.class);
                    }
                    typeHandlers.add(typeHandler);
                } catch (Exception e) {
                    typeHandlers.add(typeHandlerRegistry.getTypeHandler(Object.class));
                }
            }
            // 遍历ResultSet对象，将每一行记录都封装成map对象并添加到list中
            while (rs.next()) {
                Map<String, Object> row = new HashMap<String, Object>();
                for (int i = 0, n = columns.size(); i < n; i++) {
                    String name = columns.get(i);
                    TypeHandler<?> handler = typeHandlers.get(i);
                    row.put(name.toUpperCase(Locale.ENGLISH), handler.getResult(rs, name));
                }
                list.add(row);
            }
            return list;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                //ignore
            }
        }
    }

}
