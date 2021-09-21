package edu.zjnu.mybatis;

import edu.zjnu.mybatis.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @description: Main
 * @author: 杨海波
 * @date: 2021-09-21
 **/
public class Main {

    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("configuration-common.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = factory.openSession();

        List<User> users = session.selectList("edu.zjnu.mybatis.dao.UserDao.getAllUsers");

        users.forEach(e -> System.out.println(e.getUsername()));
        session.close();
        reader.close();
    }
}
