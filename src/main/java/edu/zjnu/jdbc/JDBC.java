package edu.zjnu.jdbc;

import java.sql.*;

/**
 * @description: JDBC
 * @author: 杨海波
 * @date: 2021-09-09
 **/
public class JDBC {

    public static void main(String[] args) {
        //一个标准的JDBC程序
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //1.加载驱动
            // DriverManager.registerDriver(new Driver());
            Class.forName("com.mysql.jdbc.Driver");//加载类时就会加载驱动,需要导入jar包

            //2.获得链接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis", "root", "Yhb199605"); //替换成自己的数据库名称

            //3.创建执行SQL语句的对象，执行SQl
            //3.1 创建执行SQL的对象
            String str = "SELECT * FROM user";
            statement = conn.createStatement();

            //3.2 执行SQL语句
            resultSet = statement.executeQuery(str);

            while (resultSet.next()) {
                int uid = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                System.out.println(uid + "  " + username + "  " + password + "  ");
            }

            // 数据库元数据
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println(metaData.getMaxColumnNameLength());
            System.out.println(metaData.getURL());
            System.out.println(metaData.getUserName());
            System.out.println(metaData.getDatabaseMajorVersion());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //4.释放资源
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}


