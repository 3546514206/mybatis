package edu.zjnu.mybatis.dao;

import edu.zjnu.mybatis.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author: wangzhenqing
 * @data: 2015-06-25 16:08:35
 * @Description:userdao方法
 */
public interface UserDao {

    User getUserByName(String username);

    User getUserByDynamicSql(Map map);

    List<User> getUsersByCache();

    boolean modifyUser(String username, String password);

    boolean deleteUser(String username);

    int addUser(User user);

    List<User> getAllUsers();

    List<User> getUserList(int id);

    void modifyAgeAddOneById(int i);
}
