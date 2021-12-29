package edu.zjnu.mybatis;

import edu.zjnu.mybatis.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: todo
 * @author: 杨海波
 * @date: 2021-12-29
 **/
public class DynamicSqlMain {

    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("configuration-cache.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = factory.openSession();

        Map<String, String> map = new HashMap<>();
        map.put("id", "2");
        map.put("username", "admin");
        map.put("password", "admin");

        User user = session.selectOne("edu.zjnu.mybatis.dao.UserDao.getUserByDynamicSql", map);
        System.out.println(user.getUsername());

        session.close();
        reader.close();
    }
}
