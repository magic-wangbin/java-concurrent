package com.concurrent.delay.queue;

/**
 * 订单.
 */
public class Order {
    private final String orderNo;
    private final Double orderMoney;

    public Order(String orderNo, Double orderMoney) {
        this.orderNo = orderNo;
        this.orderMoney = orderMoney;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }
}
