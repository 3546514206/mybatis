package edu.zjnu.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.jdbc.SQL;
import org.junit.Test;

/**
 * @description: SQL工具类
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class SQLTest {

    @Test
    public void test() {
        SQL sql = new SQL().SELECT("T.ID,T.NAME").FROM("USER P").WHERE("ID = 1").AND().WHERE("NAME LIKE '%杨%'");
        System.out.println(sql);
    }

}
