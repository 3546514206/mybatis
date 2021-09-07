package edu.zjnu.mybatis.dao;

import edu.zjnu.mybatis.model.Role;

import java.util.List;

/**
 * @author: wangzhenqing
 * @data: 2015-04-16 16:34:18
 * @Description: Roledao方法
 */
public interface RoleDao {

    boolean deleteRole(String rolename);

    boolean addRole(Role role);

    Role getRoleByName(String rolename);

    List<Role> getAllRoles();

    List<Role> getRoleList(int id);
}
