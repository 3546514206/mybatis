package edu.zjnu.mybatis.model;

import java.util.List;

/**
 * @description: UserVO
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class UserVO {

    private Integer age;

    private String name;

    List<OrderVO> orders;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderVO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderVO> orders) {
        this.orders = orders;
    }

    public UserVO(Integer age, String name, List<OrderVO> orders) {
        this.age = age;
        this.name = name;
        this.orders = orders;
    }
}
