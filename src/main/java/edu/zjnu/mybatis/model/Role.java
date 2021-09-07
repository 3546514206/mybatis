package edu.zjnu.mybatis.model;

import java.util.List;

/**
 * @author: wangzhenqing
 * @data: 15/4/16 下午4:28
 * @Description:用户角色表,对应多个用户
 */
public class Role {
    private int id;
    private String rolename;
    private String alias;
    private List<User> userList;

    public Role(String rolename, String alias) {
        this.rolename = rolename;
        this.alias = alias;
    }

    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
