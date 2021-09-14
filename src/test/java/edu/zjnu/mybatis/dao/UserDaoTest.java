package edu.zjnu.mybatis.dao;

import edu.zjnu.mybatis.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

/**
 * @author: 杨海波
 * @data: 2015年04月13日16:11:58
 * @Description:UserDao测试
 */

public class UserDaoTest {

    private Log log = LogFactory.getLog(this.getClass());

    private SqlSessionFactory sqlSessionFactory;

    private Reader reader;

    private SqlSession session;

    private UserDao userDao;

    @Before
    public void init() throws Exception {
        reader = Resources.getResourceAsReader("configuration.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        userDao = session.getMapper(UserDao.class);
    }

    @After
    public void close() throws Exception {
        if (session != null) {
            session.close();
        }
        if (reader != null) {
            reader.close();
        }
    }

    @Test
    public void testGetUserByName() throws Exception {
        log.info("testGetUserByName");
        printUserInfo("zhenqing.wang");
        log.info("testGetUserByName");

    }

    @Test
    public void testModifyUser() throws Exception {
        log.info("testModifyUser");
        boolean result = userDao.modifyUser("zhenqing.wang", "abc");
        session.commit();
        printUserInfo("zhenqing.wang");
        log.info("testModifyUser: " + result);

    }

    @Test
    public void testDeleteUser() throws Exception {
        log.info("testDeleteUser");
        boolean result = userDao.deleteUser("zhenqing.wang");
        session.commit();
        printUserInfo("zhenqing.wang");
        log.info("testDeleteUser: " + result);

    }

    @Test
    public void testAddUser() throws Exception {
        log.info("testAddUser");
        User user = new User("zhenqing.wang", "abc");
        int result = userDao.addUser(user);
        session.commit();
        printUserInfo("zhenqing.wang");
        log.info("testAddUser: " + result);
        log.info("testAddUser id: " + user.getId());


    }

    @Test
    public void testGetAllUsers() throws Exception {
        log.info("testGetAllUsers");
        List<User> userList = userDao.getAllUsers();
        int size = userList.size();
        log.info("testGetAllUsers size:" + size);
        log.info("testGetAllUsers username=" + (size != 0 ? userList.get(0).getUsername() : "null"));

    }

    @Test
    public void testGetUserList() throws Exception {
        log.info("testGetUserList");
        List<User> userList = userDao.getUserList(4);
        int size = userList.size();
        log.info("testGetUserList size:" + size);


    }

    private void printUserInfo(String username) {
        User user = userDao.getUserByName(username);
        if (user != null) {
            log.info("username=" + user.getUsername() + ",password=" + user.getPassword());
        } else {
            log.error("user is null");
        }
    }


}