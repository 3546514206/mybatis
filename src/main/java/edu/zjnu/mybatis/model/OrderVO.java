package edu.zjnu.mybatis.model;

/**
 * @description: OrderVO
 * @author: 杨海波
 * @date: 2021-09-13
 **/
public class OrderVO {

    private int orderId;

    private String name;

    private double price;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderVO(int orderId, String name, double price) {
        this.orderId = orderId;
        this.name = name;
        this.price = price;
    }
}
