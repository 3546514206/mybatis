package edu.zjnu.mybatis.model;

import java.util.List;

/**
 * @author: wangzhenqing
 * @data:2015年04月16日21:38:47
 * @Description:用户表，对应多个权限
 */
public class User {

    private int id;
    private String username;
    private String password;
    private List<Role> roleList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
