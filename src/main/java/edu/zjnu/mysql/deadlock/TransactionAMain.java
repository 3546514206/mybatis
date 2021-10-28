package edu.zjnu.mysql.deadlock;

import edu.zjnu.mybatis.dao.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @description: todo
 * @author: 杨海波
 * @date: 2021-10-21
 **/
public class TransactionAMain {
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


        // 更新id=1
        userDao.modifyAgeAddOneById(1);
        // 断点
        System.out.println();
        //更新id=2的记录
        //userDao.modifyAgeAddOneById(2);
        session.commit();


        session.close();
        try {
            assert reader != null;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
