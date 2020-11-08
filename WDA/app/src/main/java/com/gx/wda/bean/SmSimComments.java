package com.gx.wda.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * @author 
 * 
 */
public class SmSimComments implements Serializable {
    /**
     * 主键ID
     */
    private Integer commentsid;

    /**
     * 评论人ID
     */
    private Integer userid;

    /**
     * 公告ID
     */
    private Integer noticeid;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论发布时间
     */
    private Date commentdate;

    /**
     * 回复ID(数据为本表的主键ID，实现树形结构表)
     */
    private Integer replyid;

    /**
     * 回复对象ID
     */
    private Integer criticid;
    
    //额外字段
    //userName
    private String username;
    private String cirticname;
    //下级评论总数
    private Integer replycount;

    private static final long serialVersionUID = 1L;

    public Integer getCommentsid() {
        return commentsid;
    }

    public void setCommentsid(Integer commentsid) {
        this.commentsid = commentsid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(Integer noticeid) {
        this.noticeid = noticeid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentdate() {
        return commentdate;
    }


    public void setCommentdate(Date commentdate) {
        this.commentdate = commentdate;
    }

    public Integer getReplyid() {
        return replyid;
    }

    public void setReplyid(Integer replyid) {
        this.replyid = replyid;
    }

    public Integer getCriticid() {
        return criticid;
    }

    public void setCriticid(Integer criticid) {
        this.criticid = criticid;
    }
    public String getCirticname() {
        return cirticname;
    }

    public void setCirticname(String cirticname) {
        this.cirticname = cirticname;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getReplycount() {
        return replycount;
    }

    public void setReplycount(Integer replycount) {
        this.replycount = replycount;
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
        SmSimComments other = (SmSimComments) that;
        return (this.getCommentsid() == null ? other.getCommentsid() == null : this.getCommentsid().equals(other.getCommentsid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getNoticeid() == null ? other.getNoticeid() == null : this.getNoticeid().equals(other.getNoticeid()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCommentdate() == null ? other.getCommentdate() == null : this.getCommentdate().equals(other.getCommentdate()))
            && (this.getReplyid() == null ? other.getReplyid() == null : this.getReplyid().equals(other.getReplyid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCommentsid() == null) ? 0 : getCommentsid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getNoticeid() == null) ? 0 : getNoticeid().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCommentdate() == null) ? 0 : getCommentdate().hashCode());
        result = prime * result + ((getReplyid() == null) ? 0 : getReplyid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentsid=").append(commentsid);
        sb.append(", userid=").append(userid);
        sb.append(", noticeid=").append(noticeid);
        sb.append(", content=").append(content);
        sb.append(", commentdate=").append(commentdate);
        sb.append(", replyid=").append(replyid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}