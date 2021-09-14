package edu.zjnu.mybatis;

import com.alibaba.fastjson.JSON;
import edu.zjnu.JsonPrintUtil;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.SqlRunner;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @description: SqlRunnerTest
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class SqlRunnerTest {

    @Test
    public void test() throws SQLException, IOException {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mybatis",
                "root",
                "Yhb199605"); //替换成自己的数据库名称


        SqlRunner sqlRunner = new SqlRunner(connection);

        // 查询
        Map<String, Object> map = sqlRunner.selectOne("select t.* from user t where id = ?", 2);

        System.out.println(JsonPrintUtil.formatToJson(JSON.toJSONString(map)));

        // 插入数据
        SQL insertSql = new SQL().INSERT_INTO("USER ").VALUES("PASSWORD", "?").VALUES("USERNAME", "?");
        System.out.println(insertSql);

        sqlRunner.insert(insertSql.toString(), "123", "jetty");
        sqlRunner.insert(insertSql.toString(), "321", "lose");
        sqlRunner.insert(insertSql.toString(), "111", "rose");

        // 查询所有
        List<Map<String, Object>> rsAll = sqlRunner.selectAll("select t.* from user t");
        System.out.println(JsonPrintUtil.formatToJson(JSON.toJSONString(rsAll)));

    }
}
