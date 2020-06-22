package com.hsbc.commerce.service;

import com.hsbc.commerce.creditModel.credit.Credit;
import com.hsbc.commerce.creditModel.credit.CreditType;
import com.hsbc.commerce.entity.CommerceUser;
import com.hsbc.commerce.entity.CreditConsumeHistory;

import java.util.Date;
import java.util.List;
import java.util.Map;

//用户服务
public interface UserService {
    //通过id获取用户
    CommerceUser getUserById(String userId) throws Exception;

    //查询积分的有效期
    Map<Credit, String> checkMyCreditExpirationDate(String userId) throws Exception;


    //查询积分总额
    double checkMyCreditSum(String userId)  throws Exception;

    //查询积分的消费记录
    List<CreditConsumeHistory> checkMyCreditConHistory(String userId)  throws Exception;

}
