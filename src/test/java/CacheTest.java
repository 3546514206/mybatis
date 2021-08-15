import edu.zjnu.model.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author 杨海波
 * @description 缓存测试
 * @date 2021-03-03 21:28
 */
public class CacheTest extends BaseTest {

    /**
     * 一级缓存测试
     */
    @Test
    public void testLevel1Cache() {
        try (SqlSession sqlSession = getSqlSession()) {

            User user = sqlSession.selectOne(selectByID, 1);
            User user1 = sqlSession.selectOne(selectByID, 1);

            log.info(user.hashCode());
            log.info(user1.hashCode());
            String parse = null;
            String value = String.valueOf(parse);
            log.info(value);
        }
    }
}
