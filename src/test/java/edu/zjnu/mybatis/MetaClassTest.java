package edu.zjnu.mybatis;

import com.alibaba.fastjson.JSON;
import com.sun.tools.corba.se.idl.toJavaPortable.DefaultFactory;
import edu.zjnu.JsonPrintUtil;
import edu.zjnu.mybatis.model.OrderVO;
import org.apache.ibatis.reflection.MetaClass;

/**
 * @description: MetaClassTest
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class MetaClassTest {

    public static void main(String[] args) {
        MetaClass metaClass = MetaClass.forClass(OrderVO.class);
        String[] getterName = metaClass.getGetterNames();
        System.out.println(JsonPrintUtil.formatToJson(JSON.toJSONString(getterName)));
    }
}
