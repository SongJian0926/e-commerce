package com.hsbc.commerce.entity;



import java.util.Date;

public class CreditConsumeHistory {


    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    private String optionType; // Increase 增加积分 consumption 消费积分

    public double getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(double creditNum) {
        this.creditNum = creditNum;
    }

    private double creditNum;
    private Date date; //增加/消费 时间
    private User user;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private Order order;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}
