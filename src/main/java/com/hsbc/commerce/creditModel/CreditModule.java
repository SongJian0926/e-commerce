package com.hsbc.commerce.creditModel;

import com.hsbc.commerce.creditModel.credit.Credit;
import com.hsbc.commerce.creditModel.credit.CreditOption;
import com.hsbc.commerce.creditModel.credit.CreditType;
import com.hsbc.commerce.dao.CreditMapper;
import com.hsbc.commerce.entity.Constant;
import com.hsbc.commerce.entity.User;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CreditModule implements CreditModel {

    private User user;
    public CreditModule(User user){
        this.user = user;
    }
    private List<Credit> credits;
    private Double credits_sum;
    private Double consume_credits_sum = 0.00;
    CreditMapper creditMapper;


    //添加积分
    @Override
    public Credit increaseUserCredit(CreditType creditType, CreditOption creditOption) {
        double increaseCreditNum = creditOption.increaseCredit();
         //todo creditsMapper.findCreditByUser(this.user) 可以查询出来
         // 为了便于测试,所以这里没有执行Dao

        Date increaseCreditDate =new Date();
        SimpleDateFormat df = new SimpleDateFormat(Constant.DATEFORMAT);
        Credit credit;
        if (credits == null) {
            credits = new ArrayList<>();
        }
        credit = new Credit(creditType,increaseCreditNum,df.format(increaseCreditDate));

        //todo 没必要set到list，可以add到表里面。 creditsMapper.insertCredits(credit)
        // 为了便于测试,所以这里没有执行Dao
        credits.add(credit);
        return credit;
    }

    //得到用户积分
    @Override
    public Double getUserCredit() throws Exception {
        credits_sum = 0.00;
        //todo creditsMapper.findCreditByUser(this.user) 可以查询出来
        // 为了便于测试,所以这里没有执行Dao

        if (credits != null) {
            credits_sum = credits.stream().mapToDouble(Credit::getCreditNum).sum();
            //用户积分 = 用户获得的所 - 消费积分 - 过期积分
            return credits_sum - consume_credits_sum - getExpr_credits_sum();
        }
        return credits_sum;
    }

    //消费用户积分
    @Override
    public Double consumeUserCredit(Double consumeCreditNum)throws Exception {
        if (this.getUserCredit() >= consumeCreditNum) {
            this.consume_credits_sum = consumeCreditNum;
            //todo 消费记录, 可以操作消费记录表，把数据写入到几率表
            // 消费记录Dao.inset消费记录(消费记录)
        }else {
            throw new Exception("积分不足 "+consumeCreditNum +" 消费失败");
        }
        return this.getUserCredit();
    }

    //获取积分过期时间
    @Override
    public Map<Credit,String > getUserCreditExprDate() throws Exception {

        if (credits!=null) {
            Map<Credit,String> creditTypeExpDate = new HashMap<>();
            credits.forEach((credit)->{
                //获取到过期的周期
                CreditType creditType = credit.getCreditType();
                //获取到创建的时间
                String createCreditDate = credit.getCreateCreditDate();
                //计算过期的日期：
                String expirationDate = getExpirationDate(creditType, createCreditDate);
                creditTypeExpDate.put(credit,expirationDate);
            });
            return creditTypeExpDate;
        }
        throw new Exception("用户没有存在的积分");
    }
    //计算积分过期时间
    private String getExpirationDate(CreditType creditType,
                                  String createCreditDate){

        SimpleDateFormat df = new SimpleDateFormat(Constant.DATEFORMAT);
        Date date= null;
        try {
            date = df.parse(createCreditDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //在创建时间的基础上 计算出过期时间
        calendar.add(creditType.getDateUnit(),
                creditType.getExpirationDate());
        Date expirationDate = calendar.getTime();
        return df.format(expirationDate);
    }
    //计算过期积分
    private Double getExpr_credits_sum() throws Exception {
        Double expr_credits_sum=0.00;
        Map<Credit, String> userCreditExprDate = getUserCreditExprDate();
        for (Map.Entry<Credit, String> creditExprDate : userCreditExprDate.entrySet()){
            String exprDate = creditExprDate.getValue();
            SimpleDateFormat df = new SimpleDateFormat(Constant.DATEFORMAT);
            Date exprDateFormat = df.parse(exprDate);
            //条件成立 ，积分过期
            if (exprDateFormat.before(new Date())) {
                Double creditByType = getByHashCode(creditExprDate.getKey().hashCode());
                expr_credits_sum = expr_credits_sum + creditByType;
            }
        }
        return expr_credits_sum;
    }
    //通过hashCode获取指定积分
    private Double getByHashCode(int hashCode){
        AtomicReference<Double> creditNum = new AtomicReference<>(0.00);
        credits.forEach(credit -> {
            if (credit.hashCode() == hashCode) {
                creditNum.set(credit.getCreditNum());
            }
        });
        return creditNum.get();
    }
    //通过类型获取积分
    public Double getByType(CreditType creditType){
        AtomicReference<Double> creditNum = new AtomicReference<>(0.00);
        credits.forEach(credit -> {
            if (credit.getCreditType() == creditType) {
                creditNum.set(creditNum.get() + credit.getCreditNum());
            }
        });
        return creditNum.get();
    }

    @Override
    public User getUser() {
        return user;
    }

}
