package com.gx.po;

import java.io.Serializable;
import java.util.Date;

/**
 * inform
 * @author 
 */
public class Inform implements Serializable {
    /**
     * 通知ID
     */
    private Integer informId;

    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 通知标题
     */
    private String informTitle;

    /**
     * 通知内容
     */
    private String informContent;

    /**
     * 创建时间
     */
    private Date creationTime;

    private static final long serialVersionUID = 1L;

    public Integer getInformId() {
        return informId;
    }

    public void setInformId(Integer informId) {
        this.informId = informId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getInformTitle() {
        return informTitle;
    }

    public void setInformTitle(String informTitle) {
        this.informTitle = informTitle;
    }

    public String getInformContent() {
        return informContent;
    }

    public void setInformContent(String informContent) {
        this.informContent = informContent;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
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
        Inform other = (Inform) that;
        return (this.getInformId() == null ? other.getInformId() == null : this.getInformId().equals(other.getInformId()))
            && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
            && (this.getInformTitle() == null ? other.getInformTitle() == null : this.getInformTitle().equals(other.getInformTitle()))
            && (this.getInformContent() == null ? other.getInformContent() == null : this.getInformContent().equals(other.getInformContent()))
            && (this.getCreationTime() == null ? other.getCreationTime() == null : this.getCreationTime().equals(other.getCreationTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInformId() == null) ? 0 : getInformId().hashCode());
        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
        result = prime * result + ((getInformTitle() == null) ? 0 : getInformTitle().hashCode());
        result = prime * result + ((getInformContent() == null) ? 0 : getInformContent().hashCode());
        result = prime * result + ((getCreationTime() == null) ? 0 : getCreationTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", informId=").append(informId);
        sb.append(", adminId=").append(adminId);
        sb.append(", informTitle=").append(informTitle);
        sb.append(", informContent=").append(informContent);
        sb.append(", creationTime=").append(creationTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}