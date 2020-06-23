package com.hsbc.commerce.creditModel;

import com.hsbc.commerce.creditModel.credit.Credit;
import com.hsbc.commerce.creditModel.credit.CreditOption;
import com.hsbc.commerce.creditModel.credit.CreditType;

import java.util.Map;

public interface CreditModel {
    //添加积分
    Credit increaseUserCredit(CreditType creditType, CreditOption creditOption);

    //得到用户积分
    Double getUserCredit() throws Exception;

    //消费用户积分
    Double consumeUserCredit(Double consumeCreditNum) throws Exception;

    //获取积分有效期
    Map<Credit, String> getUserCreditExprDate() throws Exception;

    //得到积分所属的用户
    User getUser();

}
