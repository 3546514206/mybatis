package edu.zjnu.mybatis;

import edu.zjnu.mybatis.dao.UserDao;
import edu.zjnu.mybatis.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/**
 * @description: todo
 * @author: 杨海波
 * @date: 2021-10-01
 **/
public class Level1CacheDirtyReadingBMain {

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    private static SqlSession session;
    private static UserDao userDao;
    private Log log = LogFactory.getLog(this.getClass());

    public static void init() throws Exception {
        reader = Resources.getResourceAsReader("configuration-common.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        userDao = session.getMapper(UserDao.class);
    }

    public static void close() throws Exception {
        if (session != null) {
            session.close();
        }
        if (reader != null) {
            reader.close();
        }
    }

    /**
     * B负责更新数据
     */
    public static void testB() throws Exception {


        //第一次查询之后
        User user1 = userDao.getUserByName("admin");
        System.out.println(user1.getUsername() + ":" + user1.getPassword());
        System.out.println("断点");

        //第二次查询，是否能查询到A中的更新？
        User user2 = userDao.getUserByName("admin");
        System.out.println(user2.getUsername() + ":" + user2.getPassword());
    }

    public static void main(String[] args) throws Exception {
        init();
        testB();
        close();
    }

}
