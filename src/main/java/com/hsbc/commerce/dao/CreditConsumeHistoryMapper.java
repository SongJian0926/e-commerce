package com.hsbc.commerce.dao;

import com.hsbc.commerce.entity.CreditConsumeHistory;
import com.hsbc.commerce.creditModel.User;

import java.util.List;

public interface CreditConsumeHistoryMapper {

    int insertCreditConsumeHistory(CreditConsumeHistory creditConsumeHistory);

    List<CreditConsumeHistory> findByUser(User user);
}
