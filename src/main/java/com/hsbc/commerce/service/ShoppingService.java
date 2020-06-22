package com.hsbc.commerce.service;

import com.hsbc.commerce.entity.Goods;

import java.util.List;

//购物服务
public interface ShoppingService {

    void shopping(String orderByUserId, Double amount, List<Goods> goods) throws Exception;
}
