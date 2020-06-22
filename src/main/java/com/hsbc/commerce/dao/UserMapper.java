package com.hsbc.commerce.dao;

import com.hsbc.commerce.entity.CommerceUser;

public interface UserMapper {

    //通过id查询user
    CommerceUser findUserById(String id);
}
