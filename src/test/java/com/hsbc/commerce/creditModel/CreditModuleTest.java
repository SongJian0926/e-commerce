package com.hsbc.commerce.creditModel;

import com.hsbc.commerce.creditModel.credit.CreditType;
import com.hsbc.commerce.entity.CommerceUser;
import com.hsbc.commerce.entity.User;
import org.junit.Test;

public class CreditModuleTest {

    @Test
    public void increaseUserCredit() throws Exception {
        User user = new CommerceUser("张三","00001");
        CreditModule creditModule = new CreditModule(user);
        // 消费0-500 增加的积分
        creditModule.increaseUserCredit(CreditType.consumeCredit,()->{
           return 500*0.1;
        });
        System.out.println(creditModule.getUserCredit());
        //消费 500-1000 增加积分
        creditModule.increaseUserCredit(CreditType.consumeCredit,()->{
            return 1000*0.15;
        });
        System.out.println(creditModule.getUserCredit());
        //消费 500-1000 增加积分
        creditModule.increaseUserCredit(CreditType.consumeCredit,()->{
            //消费1000 以上
            return 12000*0.20;
        });
        //查询积分
        System.out.println(creditModule.getUserCredit());

        //评论获取积分
        creditModule.increaseUserCredit(CreditType.commentCredit,()->{
            return 5*5;
        });
        //查询积分
        System.out.println(creditModule.getUserCredit());

        //消费积分
        System.out.println(creditModule.consumeUserCredit(125.00));
    }
}
