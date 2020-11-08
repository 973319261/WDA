package com.gx.po;

import java.io.Serializable;

/**
 * notice_inform
 * @author 
 */
public class NoticeInform implements Serializable {
    /**
     * 公告通知ID
     */
    private Integer noticeInformId;

    /**
     * 公告ID
     */
    private Integer noticeId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 是否已读
     */
    private Boolean isRead;

    private static final long serialVersionUID = 1L;

    public Integer getNoticeInformId() {
        return noticeInformId;
    }

    public void setNoticeInformId(Integer noticeInformId) {
        this.noticeInformId = noticeInformId;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
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
        NoticeInform other = (NoticeInform) that;
        return (this.getNoticeInformId() == null ? other.getNoticeInformId() == null : this.getNoticeInformId().equals(other.getNoticeInformId()))
            && (this.getNoticeId() == null ? other.getNoticeId() == null : this.getNoticeId().equals(other.getNoticeId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getIsRead() == null ? other.getIsRead() == null : this.getIsRead().equals(other.getIsRead()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNoticeInformId() == null) ? 0 : getNoticeInformId().hashCode());
        result = prime * result + ((getNoticeId() == null) ? 0 : getNoticeId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getIsRead() == null) ? 0 : getIsRead().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", noticeInformId=").append(noticeInformId);
        sb.append(", noticeId=").append(noticeId);
        sb.append(", userId=").append(userId);
        sb.append(", isRead=").append(isRead);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}