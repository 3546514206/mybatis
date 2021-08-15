import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.Reader;

/**
 * @author 杨海波
 * @description 测试类基类
 * @date 2021-02-22 11:28
 */
public class BaseTest {

    protected static final Logger log = Logger.getLogger(BaseTest.class);

    private static SqlSessionFactory sqlSessionFactory;

    protected static String selectByID = "edu.zjnu.mapper.UserMapper.selectById";

    protected static String selectAll = "edu.zjnu.mapper.UserMapper.selectAll";

    @BeforeClass
    public static void init() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
