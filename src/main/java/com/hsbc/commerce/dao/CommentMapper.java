package com.hsbc.commerce.dao;

import com.hsbc.commerce.entity.Comment;

import java.util.List;

public interface CommentMapper {

    int insertComment(Comment comment);

    int updateComment(Comment comment);
    List<Comment> getCommentByUserIdAndStatusAndIsCredit(String userId,int status,boolean isCredit);
}
