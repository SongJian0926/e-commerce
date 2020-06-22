package com.hsbc.commerce.service.impl;


import com.hsbc.commerce.creditModel.CreditModule;
import com.hsbc.commerce.creditModel.credit.CreditType;
import com.hsbc.commerce.entity.CommerceUser;
import com.hsbc.commerce.entity.User;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShoppingServiceImplTest {

    @Test
    public void consumptionReductionByCredit() throws Exception {
        User user = new CommerceUser("张三","00001");
        ShoppingServiceImpl s = new ShoppingServiceImpl();
        CreditModule creditModule = new CreditModule(user);
        // 消费0-500 增加的积分 50
        s.increaseCredit(500.00,creditModule);
        //消费 500-1000 120
        s.increaseCredit(800.0,creditModule);
        //消费 1000以上 240
        s.increaseCredit(1200.0,creditModule);
        //评论获取积分 25 == 410+25
        creditModule.increaseUserCredit(CreditType.commentCredit,()->{
            return 5*5;
        });
        System.out.println(creditModule.getByType(CreditType.consumeCredit));
        System.out.println(creditModule.getByType(CreditType.commentCredit));
        System.out.println("当前用户的积分 "+creditModule.getUserCredit());
        System.out.println("当前用户积分的过期时间 "+creditModule.getUserCreditExprDate().toString());
        System.out.println("用户消费金额：12.56");
        System.out.println("减免后金额"+s.ConsumptionReductionByCredit(12.56, creditModule,null));
        System.out.println("消费后剩余用户积分 " +creditModule.getUserCredit());
        System.out.println("============================");
        System.out.println("用户消费金额：800.56");
        System.out.println("减免后金额"+s.ConsumptionReductionByCredit(800.56, creditModule,null));
        System.out.println("消费后剩余用户积分 " +creditModule.getUserCredit());
    }

    @Test
    public void testMain(){
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(day));

    }

}
