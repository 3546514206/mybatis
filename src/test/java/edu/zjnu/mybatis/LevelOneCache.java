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
 * @description: LevelOneCache
 * @author: 杨海波
 * @date: 2021-09-29
 **/
public class LevelOneCache {

    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("configuration-cache.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = factory.openSession();

        Map<String, String> map = new HashMap<>();
        map.put("username", "zhenqing.wang");

        User user = session.selectOne("edu.zjnu.mybatis.dao.UserDao.getUserByName", map);
        System.out.println(user.hashCode());

        User user1 = session.selectOne("edu.zjnu.mybatis.dao.UserDao.getUserByName", map);
        System.out.println(user1.hashCode());

        session.close();
        reader.close();
    }
}
