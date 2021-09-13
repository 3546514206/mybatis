package edu.zjnu.mybatis;

import edu.zjnu.mybatis.model.OrderVO;
import edu.zjnu.mybatis.model.User;
import edu.zjnu.mybatis.model.UserVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: MetaObjectTest
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class MetaObjectTest {


    @Test
    public void test() throws SQLException, IOException {
        List<OrderVO> orderVOS = new ArrayList<OrderVO>() {  //外层花括号是定义内部类
            { //内层花括号实际上是在内部类中书写代码块，在内部类被加载的时候执行
                add(new OrderVO(1, "一大包泡面", 200));
                add(new OrderVO(2, "一块牛肉", 30));
            }
        };

        UserVO userVO = new UserVO(25, "杨海波", orderVOS);

        MetaObject metaObject = SystemMetaObject.forObject(userVO);
        System.out.println(metaObject.getValue("orders[0].name"));
        System.out.println(metaObject.getValue("orders[1].name"));
        System.out.println(metaObject.hasGetter("name"));


    }
}
