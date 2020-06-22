package com.hsbc.commerce.entity;

public class Constant {
    //Comment评论表常量
    public static final int commentStatusIsNormal = 1; //正常评论
    public static final int commentStatusIsDelete = -1;//删除评论
    public static final int commentNumberGetCredit = 5; //获取积分数=评论次数x 5

    //积分消费记录常量
    public static String INCREASE = "Increase"; //新增积分
    public static String CONSUMPTION = "Consumption"; //消费积分

    //日期格式
    public static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
}
