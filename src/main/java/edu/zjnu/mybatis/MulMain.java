package edu.zjnu.mybatis;

import edu.zjnu.mybatis.dao.UserDao;
import edu.zjnu.mybatis.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.List;

/**
 * @description: 一千万条数据
 * @author: 杨海波
 * @date: 2021-09-21
 **/
public class MulMain {

    public static void main(String[] args) {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("configuration-common.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);

        SqlSession session = factory.openSession();

        UserDao userDao = session.getMapper(UserDao.class);

        User user = new User();

        int count = 0;

        for (float i = 2222222; i < 9999999999f; i++) {
            user.setUsername("judy" + i);
            user.setPassword("sjuad" + i);
            user.setAge(18);
            user.setHeight(178);
            user.setAdder("china" + i);
            user.setBirth(new Date());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setIsVip(1);
            user.setCreator(1);
            user.setUpdater(1);
            count = userDao.addUser(user) + count;
            System.out.println("插入了" + count + "条数据");

        }


        session.commit();

        System.out.println(count);

        session.close();
        try {
            assert reader != null;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
