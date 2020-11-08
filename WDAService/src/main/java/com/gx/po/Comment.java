package com.gx.po;

import java.io.Serializable;
import java.util.Date;

/**
 * comment
 * @author 
 */
public class Comment implements Serializable {
    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 公告ID
     */
    private Integer noticeId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 回复评论ID
     */
    private Integer replyId;

    /**
     * 评论用户ID
     */
    private Integer criticId;

    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 回复对象的类型(0代表用户，1代表管理员)
     */
    private Integer commentType;

    /**
     * 层级类型(区分二级评论)
     */
    private Integer tierType;

    private static final long serialVersionUID = 1L;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getCriticId() {
        return criticId;
    }

    public void setCriticId(Integer criticId) {
        this.criticId = criticId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public Integer getTierType() {
        return tierType;
    }

    public void setTierType(Integer tierType) {
        this.tierType = tierType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Comment other = (Comment) that;
        return (this.getCommentId() == null ? other.getCommentId() == null : this.getCommentId().equals(other.getCommentId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getNoticeId() == null ? other.getNoticeId() == null : this.getNoticeId().equals(other.getNoticeId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreationTime() == null ? other.getCreationTime() == null : this.getCreationTime().equals(other.getCreationTime()))
            && (this.getReplyId() == null ? other.getReplyId() == null : this.getReplyId().equals(other.getReplyId()))
            && (this.getCriticId() == null ? other.getCriticId() == null : this.getCriticId().equals(other.getCriticId()))
            && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
            && (this.getCommentType() == null ? other.getCommentType() == null : this.getCommentType().equals(other.getCommentType()))
            && (this.getTierType() == null ? other.getTierType() == null : this.getTierType().equals(other.getTierType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCommentId() == null) ? 0 : getCommentId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getNoticeId() == null) ? 0 : getNoticeId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreationTime() == null) ? 0 : getCreationTime().hashCode());
        result = prime * result + ((getReplyId() == null) ? 0 : getReplyId().hashCode());
        result = prime * result + ((getCriticId() == null) ? 0 : getCriticId().hashCode());
        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
        result = prime * result + ((getCommentType() == null) ? 0 : getCommentType().hashCode());
        result = prime * result + ((getTierType() == null) ? 0 : getTierType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentId=").append(commentId);
        sb.append(", userId=").append(userId);
        sb.append(", noticeId=").append(noticeId);
        sb.append(", content=").append(content);
        sb.append(", creationTime=").append(creationTime);
        sb.append(", replyId=").append(replyId);
        sb.append(", criticId=").append(criticId);
        sb.append(", adminId=").append(adminId);
        sb.append(", commentType=").append(commentType);
        sb.append(", tierType=").append(tierType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}