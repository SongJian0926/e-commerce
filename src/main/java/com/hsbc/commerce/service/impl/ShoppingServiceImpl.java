package com.hsbc.commerce.service.impl;

import com.hsbc.commerce.creditModel.CreditModel;
import com.hsbc.commerce.creditModel.CreditModule;
import com.hsbc.commerce.creditModel.User;
import com.hsbc.commerce.creditModel.credit.Credit;
import com.hsbc.commerce.creditModel.credit.CreditType;
import com.hsbc.commerce.dao.CreditConsumeHistoryMapper;
import com.hsbc.commerce.dao.OrderMapper;
import com.hsbc.commerce.entity.*;
import com.hsbc.commerce.service.ShoppingService;
import com.hsbc.commerce.service.UserService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


//购物消费
public class ShoppingServiceImpl implements ShoppingService {

    UserService userService;
    CreditConsumeHistoryMapper creditConsumeHistoryMapper;
    OrderMapper orderMapper;
    @Override
    public void shopping(String orderByUserId, Double amount, List<Goods> goods) throws Exception {
        CommerceUser userById = userService.getUserById(orderByUserId);
        CreditModel creditModel = new CreditModule(userById);
        Order order = new Order();
        //消费增加积分
        Credit credit = increaseCredit(amount,creditModel);
        //增加积分记录
        history(userById,credit.getCreditNum(),Constant.INCREASE,order);
        //消费积分减免后的金额
        amount = ConsumptionReductionByCredit(amount,creditModel,order);
         //TODO 消费成功 操作数据库 将订单数据插入表中
         //  orderMapper.insertOrder(order);
    }
    //增加积分记录
    private void history(User user, double creditNum, String optionType, Order order){
        CreditConsumeHistory creditConsumeHistory = new CreditConsumeHistory();
        creditConsumeHistory.setUser(user);
        creditConsumeHistory.setCreditNum(creditNum);
        creditConsumeHistory.setDate(new Date());
        creditConsumeHistory.setOptionType(Constant.INCREASE);
        creditConsumeHistory.setOrder(order);
        //TODO 操作数据库 讲数据插入表中
       // creditConsumeHistoryMapper.insertCreditConsumeHistory(creditConsumeHistory);
    }
    //消费增加积分
    public Credit increaseCredit(Double amount,CreditModel creditModel) throws Exception {
        Credit credit;
        if(amount>0.00 && amount<=500.00){
            credit = creditModel.increaseUserCredit(CreditType.consumeCredit,()->{
                return  OrderType.PrimaryOrder.getRatio() * amount;
            });
        }else if(amount >500.00 && amount <=1000.00){
            credit = creditModel.increaseUserCredit(CreditType.consumeCredit,()->{
                return  OrderType.IntermediateOrder.getRatio() * amount;
            });
        }else if(amount > 1000.00){
            credit = creditModel.increaseUserCredit(CreditType.consumeCredit,()->{
                return  OrderType.AdvancedOrder.getRatio() * amount;
            });
        }else {
            throw new Exception("订单金额异常");
        }
        return credit;
    }
    //消费积分减免金额
    public Double ConsumptionReductionByCredit(Double amount,CreditModel creditModel,Order order) throws Exception {
        Double userCredit = creditModel.getUserCredit();//用户积分数
        //消耗积分
        double depleteCredit = 0.00;
        if(userCredit>10) {
            //计算当前用户的积分可以兑换多少钱
            Double eductionAmount = (userCredit - userCredit%10)/10;
            if (amount > eductionAmount) {
                //兑换所有的积分
                depleteCredit = eductionAmount * 10;
            }else{
                //计算出最多使用多少积分，当所有积分都兑换，已经大于了订单金额，所以需要计算出
                depleteCredit = BigDecimal.valueOf(amount * 10).
                        subtract(BigDecimal.valueOf((amount * 10) % 10)).
                        doubleValue();
            }
        }
        //计算出积分兑换后 订单金额变成多少（订单金额 - 当前兑换的金额）
        BigDecimal subtract = BigDecimal.valueOf(amount).subtract(BigDecimal.valueOf(depleteCredit/10));
        amount = subtract.doubleValue();
        //操作积分记录减少的积分
        creditModel.consumeUserCredit(depleteCredit);
        System.out.println("本次减免积分："+depleteCredit);
        //操作积分积分消费记录
        history(creditModel.getUser(),depleteCredit,Constant.CONSUMPTION,order);
        return amount;
    }

}
