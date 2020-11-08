package com.gx.po;

import java.io.Serializable;

/**
 * inform_detail
 * @author 
 */
public class InformDetail implements Serializable {
    /**
     * 通知明细ID
     */
    private Integer informDetailId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 通知ID
     */
    private Integer informId;

    /**
     * 是否已读
     */
    private Boolean isRead;

    private static final long serialVersionUID = 1L;

    public Integer getInformDetailId() {
        return informDetailId;
    }

    public void setInformDetailId(Integer informDetailId) {
        this.informDetailId = informDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInformId() {
        return informId;
    }

    public void setInformId(Integer informId) {
        this.informId = informId;
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
        InformDetail other = (InformDetail) that;
        return (this.getInformDetailId() == null ? other.getInformDetailId() == null : this.getInformDetailId().equals(other.getInformDetailId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getInformId() == null ? other.getInformId() == null : this.getInformId().equals(other.getInformId()))
            && (this.getIsRead() == null ? other.getIsRead() == null : this.getIsRead().equals(other.getIsRead()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInformDetailId() == null) ? 0 : getInformDetailId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getInformId() == null) ? 0 : getInformId().hashCode());
        result = prime * result + ((getIsRead() == null) ? 0 : getIsRead().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", informDetailId=").append(informDetailId);
        sb.append(", userId=").append(userId);
        sb.append(", informId=").append(informId);
        sb.append(", isRead=").append(isRead);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}