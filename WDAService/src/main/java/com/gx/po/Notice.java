package com.gx.po;

import java.io.Serializable;
import java.util.Date;

/**
 * notice
 * @author 
 */
public class Notice implements Serializable {
    /**
     * 公告ID
     */
    private Integer noticeId;

    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 公告名称
     */
    private String noticeName;

    /**
     * 公告描述
     */
    private String noticeDescribe;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 访问量
     */
    private Integer visitorVolume;

    private static final long serialVersionUID = 1L;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeDescribe() {
        return noticeDescribe;
    }

    public void setNoticeDescribe(String noticeDescribe) {
        this.noticeDescribe = noticeDescribe;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getVisitorVolume() {
        return visitorVolume;
    }

    public void setVisitorVolume(Integer visitorVolume) {
        this.visitorVolume = visitorVolume;
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
        Notice other = (Notice) that;
        return (this.getNoticeId() == null ? other.getNoticeId() == null : this.getNoticeId().equals(other.getNoticeId()))
            && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
            && (this.getNoticeName() == null ? other.getNoticeName() == null : this.getNoticeName().equals(other.getNoticeName()))
            && (this.getNoticeDescribe() == null ? other.getNoticeDescribe() == null : this.getNoticeDescribe().equals(other.getNoticeDescribe()))
            && (this.getReleaseTime() == null ? other.getReleaseTime() == null : this.getReleaseTime().equals(other.getReleaseTime()))
            && (this.getVisitorVolume() == null ? other.getVisitorVolume() == null : this.getVisitorVolume().equals(other.getVisitorVolume()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNoticeId() == null) ? 0 : getNoticeId().hashCode());
        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
        result = prime * result + ((getNoticeName() == null) ? 0 : getNoticeName().hashCode());
        result = prime * result + ((getNoticeDescribe() == null) ? 0 : getNoticeDescribe().hashCode());
        result = prime * result + ((getReleaseTime() == null) ? 0 : getReleaseTime().hashCode());
        result = prime * result + ((getVisitorVolume() == null) ? 0 : getVisitorVolume().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", noticeId=").append(noticeId);
        sb.append(", adminId=").append(adminId);
        sb.append(", noticeName=").append(noticeName);
        sb.append(", noticeDescribe=").append(noticeDescribe);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", visitorVolume=").append(visitorVolume);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}