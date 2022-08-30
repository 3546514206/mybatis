package edu.zjnu.jdbc;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @description: DataSourceMain
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class  DataSourceMain {

    public static void main(String[] args) throws IOException, SQLException {
        DataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        Properties properties = new Properties();
        InputStream config = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("jdbc.properties");

        properties.load(config);
        dataSourceFactory.setProperties(properties);

        DataSource dataSource = dataSourceFactory.getDataSource();

        Connection connection = dataSource.getConnection();

        //3.1 创建执行SQL的对象
        String sqlStr = "SELECT * FROM user";

        Statement statement = connection.createStatement();
        //3.2 执行SQL语句
        ResultSet resultSet = statement.executeQuery(sqlStr);
        while (resultSet.next()) {
            int uid = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");

            System.out.println(uid + "  " + username + "  " + password + "  ");
        }


    }

}
