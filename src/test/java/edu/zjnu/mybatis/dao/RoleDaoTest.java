package edu.zjnu.mybatis.dao;

import edu.zjnu.mybatis.model.Role;
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
 * @author: wangzhenqing
 * @data: 15/4/16 下午5:55
 * @Description:RoleDao测试
 */

public class RoleDaoTest{
    private Log log = LogFactory.getLog(this.getClass());

    private SqlSessionFactory sqlSessionFactory;

    private Reader reader;

    private SqlSession session;

    private RoleDao roleDao;

    @Before
    public void init() throws Exception {
        reader = Resources.getResourceAsReader("configuration.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        roleDao = session.getMapper(RoleDao.class);
    }

    @After
    public void close() throws Exception{
        if (session != null){
            session.close();
        }
        if (reader != null){
            reader.close();
        }
    }

    private void printRoleInfo(String rolename){
        Role role = roleDao.getRoleByName(rolename);
        if (role != null){
            log.info("rolename=" + role.getRolename() + ",alias=" + role.getAlias());
        }else{
            log.error("role is null");
        }
    }

    @Test
    public void testDeleteRole() throws Exception {
        log.info("testDeleteRole");
        String rolename = "ROLE_ADMIN";
        roleDao.deleteRole(rolename);
        session.commit();
        printRoleInfo(rolename);

    }

    @Test
    public void testAddRole() throws Exception {
        log.info("testAddRole");
        Role role = new Role();
        role.setRolename("ROLE_ADMIN");
        role.setAlias("超级管理员");
        roleDao.addRole(role);
        session.commit();
        printRoleInfo("ROLE_ADMIN");

    }

    @Test
    public void testGetAllRoles() throws Exception {
        log.info("testGetAllRoles");
        List<Role> roleList = roleDao.getAllRoles();
        log.info("roleList:" + roleList.size());
        for (Role role : roleList){
            printRoleInfo(role.getRolename());
        }

    }

    @Test
    public void testGetRoleByName() throws Exception {
        log.info("testGetRoleByName");
        printRoleInfo("ROLE_ADMIN");
    }

    @Test
    public void testGetRoleList() throws Exception {
        log.info("testGetRoleList");
        List<Role> roleList = roleDao.getRoleList(1);
        log.info("roleList:" + roleList.size());
        for (Role role : roleList){
            printRoleInfo(role.getRolename());
        }
    }
}