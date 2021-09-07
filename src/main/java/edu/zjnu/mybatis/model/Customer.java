package edu.zjnu.mybatis.model;

import java.util.List;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2015-12-03 14:40:27
 * Description: 客户
 */
public class Customer {
    private int id;
    private String name;
    private List<Order> orders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
