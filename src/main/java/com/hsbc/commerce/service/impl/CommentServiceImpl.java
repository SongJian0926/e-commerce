package com.hsbc.commerce.service.impl;

import com.hsbc.commerce.creditModel.CreditModel;
import com.hsbc.commerce.creditModel.CreditModule;
import com.hsbc.commerce.creditModel.credit.CreditType;
import com.hsbc.commerce.dao.CommentMapper;
import com.hsbc.commerce.entity.Comment;
import com.hsbc.commerce.entity.CommerceUser;
import com.hsbc.commerce.entity.Constant;
import com.hsbc.commerce.service.CommentService;
import com.hsbc.commerce.service.UserService;

import java.util.List;

//评论
public class CommentServiceImpl implements CommentService {
    CommentMapper commentMapper;
    UserService userService;

    //增加评论
    @Override
    public Comment addComment(String commentUserID,
                              String beCommentUserId,
                              String commentContent) throws Exception {
        //添加评论
        Comment comment = new Comment();
        comment.setCommentUserId(commentUserID);
        comment.setBeCommentUserId(beCommentUserId);
        comment.setCommentContent(commentContent);
        comment.setCommentStatus(Constant.commentStatusIsNormal);
        commentMapper.insertComment(comment);
        //看是否需要增加积分
        insertCommentCredit(commentUserID);
        return comment;
    }
    //增加评论积分
    public void insertCommentCredit(String commentUserID) throws Exception {
        List<Comment> comments = commentMapper.getCommentByUserIdAndStatusAndIsCredit(
                commentUserID, Constant.commentStatusIsNormal, false);

        if (comments.size() > Constant.commentNumberGetCredit) {
            CommerceUser commerceUser = userService.getUserById(commentUserID);
            CreditModel creditModel = new CreditModule(commerceUser);
            creditModel.increaseUserCredit(CreditType.consumeCredit,()->{
                Integer creditNum = comments.size() * Constant.commentNumberGetCredit;
                return creditNum.doubleValue();
            });
            comments.stream().map(comment -> {
                comment.setCredit(true);
                commentMapper.updateComment(comment);
                return comment;
            });
        }
    }
}
