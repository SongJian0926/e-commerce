package com.hsbc.commerce.service;

import com.hsbc.commerce.entity.Comment;

//评论服务
public interface CommentService {

    Comment addComment(String commentUserID, String beCommentUserId, String commentContent) throws Exception;
}
