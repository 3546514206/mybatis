import edu.zjnu.model.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author 杨海波
 * @description User测试类
 * @date 2021-02-22 11:30
 */
public class UseTest extends BaseTest {

    @Test
    public void testUserSelectAll() {

        try (SqlSession sqlSession = getSqlSession()) {
            log.info(sqlSession.getClass());
            List<User> users = sqlSession.selectList(selectAll);
            for (User user : users) {
                log.info(user.getUserName());
            }
        }
    }

    @Test
    public void testUserSelectById() {

        try (SqlSession sqlSession = getSqlSession()) {
            User user = sqlSession.selectOne(selectByID, 1);
            log.info(user.getUserName());
        }
    }
}
