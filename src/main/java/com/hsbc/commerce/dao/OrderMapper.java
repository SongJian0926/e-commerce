package com.hsbc.commerce.dao;

import com.hsbc.commerce.entity.Order;

public interface OrderMapper {

    //增加订单
    int  insertOrder(Order order);
}
