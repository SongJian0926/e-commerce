package com.hsbc.commerce.dao;

import com.hsbc.commerce.creditModel.credit.Credit;
import com.hsbc.commerce.creditModel.User;

import java.util.List;

public interface CreditMapper {

    List<Credit> findCreditByUser(User user);
    Credit insertCredits(Credit credit);
}
