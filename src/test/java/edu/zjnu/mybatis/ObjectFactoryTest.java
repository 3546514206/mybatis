package edu.zjnu.mybatis;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.util.List;

/**
 * @description: ObjectFactoryTest
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class ObjectFactoryTest {

    public static void main(String[] args) {
        ObjectFactory objectFactory = new DefaultObjectFactory();
        List<Integer> list = objectFactory.create(List.class);
        list.add(1);
        list.add(2);
        System.out.println(list);
    }
}
