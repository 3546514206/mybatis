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
public class Level1CacheDirtyReadingAMain {

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    private static SqlSession session;
    private static UserDao userDao;
    private Log log = LogFactory.getLog(this.getClass());

    public static void init() throws Exception {
        reader = Resources.getResourceAsReader("configuration-common.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession(true);
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
     * A负责更新数据
     */
    public static void testA() throws Exception {
        userDao.modifyUser("admin", "changed");
        User u = userDao.getUserByName("admin");
        System.out.println(u.getUsername() + ":" + u.getPassword());

    }

    public static void main(String[] args) throws Exception {
        init();
        testA();
        close();
    }

}
