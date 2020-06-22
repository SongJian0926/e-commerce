package com.hsbc.commerce.creditModel.credit;

import java.util.Date;

//积分类
public class Credit {

    private CreditType creditType;

    public void setCreateCreditDate(String createCreditDate) {
        this.createCreditDate = createCreditDate;
    }

    private String createCreditDate;
    private double creditNum;

    public void setCreditNum(double creditNum,String createCreditDate){
        this.creditNum = creditNum;
        this.createCreditDate = createCreditDate;

    }

    public Credit(CreditType creditType, double creditNum,String createCreditDate){
        this.creditType = creditType;
        this.creditNum = creditNum;
        this.createCreditDate = createCreditDate;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public String getCreateCreditDate() {
        return createCreditDate;
    }

    public Double getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(double creditNum) {
        this.creditNum = creditNum;
    }
}
