package edu.zjnu.mybatis;

import edu.zjnu.mybatis.dao.RoleDao;
import edu.zjnu.mybatis.model.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @description: SqlSessionCreatMain
 * @author: 杨海波
 * @date: 2021-09-16
 **/
public class SqlSessionCreatMain {

    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("configuration-common.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = factory.openSession();
        // 一次数据库会话不是被局限在Mapper中的，而是跨Mapepr的，可以根据需要获取不同的Mapper实例
        RoleDao dao = session.getMapper(RoleDao.class);
        List<Role> roles = dao.getAllRoles();
        System.out.println(roles.size());
        session.close();
        reader.close();
    }
}
