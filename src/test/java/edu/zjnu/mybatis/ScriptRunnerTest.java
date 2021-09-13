package edu.zjnu.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description: ScriptRunnerTest
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class ScriptRunnerTest {

    @Test
    public void test() throws SQLException, IOException {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mybatis",
                "root",
                "Yhb199605"); //替换成自己的数据库名称

        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setFullLineDelimiter(false);
        scriptRunner.runScript(Resources.getResourceAsReader("create.sql"));

    }
}
