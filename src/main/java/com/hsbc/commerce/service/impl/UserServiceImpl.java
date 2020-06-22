package com.hsbc.commerce.service.impl;

import com.hsbc.commerce.creditModel.CreditModel;
import com.hsbc.commerce.creditModel.CreditModule;
import com.hsbc.commerce.creditModel.credit.Credit;
import com.hsbc.commerce.creditModel.credit.CreditType;
import com.hsbc.commerce.dao.CreditConsumeHistoryMapper;
import com.hsbc.commerce.dao.UserMapper;
import com.hsbc.commerce.entity.CommerceUser;
import com.hsbc.commerce.entity.CreditConsumeHistory;

import com.hsbc.commerce.service.UserService;

import java.util.List;
import java.util.Map;

//用户操作
public class UserServiceImpl implements UserService {

    UserMapper userMapper;
    CreditConsumeHistoryMapper creditConsumeHistoryMapper;
    //查询用户
    @Override
    public CommerceUser getUserById(String id) throws Exception {
        CommerceUser user = userMapper.findUserById(id);
        if (user!=null) {
            return user;
        }else {
            throw new Exception("用户不存在。");
        }
    }

    //查询用户积分的有效期
    @Override
    public Map<Credit, String> checkMyCreditExpirationDate(String userId) throws Exception {
        CommerceUser user = getUserById(userId);
        CreditModel creditModel = new CreditModule(user);
        Map<Credit, String> userCreditExprDate = creditModel.getUserCreditExprDate();
        return userCreditExprDate;
    }
    //查询用户积分
    @Override
    public double checkMyCreditSum(String userId) throws Exception {
        CommerceUser user = getUserById(userId);
        CreditModel creditModel = new CreditModule(user);
        Double userCredit = creditModel.getUserCredit();
        return userCredit;
    }

    //查询云集积分消费记录
    @Override
    public List<CreditConsumeHistory> checkMyCreditConHistory(String userId) {
        CommerceUser userById = userMapper.findUserById(userId);
        List<CreditConsumeHistory> CCHistoryByUsers = creditConsumeHistoryMapper.findByUser(userById);
        return CCHistoryByUsers;
    }
}
