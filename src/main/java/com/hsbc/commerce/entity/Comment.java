package com.hsbc.commerce.entity;

public class Comment {
    private String commentUserId;
    private String beCommentUserId;
    private String commentContent;
    private int commentStatus; //评论状态 1 正常 -1删除
    private boolean isCredit; //是否获取过积分 false 未获取 true 获取过积分

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getBeCommentUserId() {
        return beCommentUserId;
    }

    public void setBeCommentUserId(String beCommentUserId) {
        this.beCommentUserId = beCommentUserId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        isCredit = credit;
    }


}
