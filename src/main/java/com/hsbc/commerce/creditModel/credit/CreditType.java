package com.hsbc.commerce.creditModel.credit;

import java.util.Calendar;

//积分类型
public enum CreditType {
    consumeCredit(2, Calendar.MONTH), //消费积分
    commentCredit(3,Calendar.MONTH), //评论积分
    signInCredit(4,Calendar.MONTH); //签到积分

    public int getExpirationDate() {
        return expirationDate;
    }

    private int expirationDate;

    public int getDateUnit() {
        return dateUnit;
    }

    private int dateUnit;


    CreditType(int expirationDate,int dateUnit){
        this.expirationDate = expirationDate;
        this.dateUnit = dateUnit;
    }


}
