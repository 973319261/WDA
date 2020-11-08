package com.gx.wda.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * version
 * @author 
 */
public class Version implements Serializable {
    /**
     * 版本ID
     */
    private Integer versionId;

    /**
     * 版本号
     */
    private Integer versionNumber;

    /**
     * 版本标题
     */
    private String versionTitle;

    /**
     * apk地址
     */
    private String apkUrl;

    /**
     * 创建日期
     */
    private Date creationDate;

    /**
     * 是否强制
     */
    private Boolean isForce;

    /**
     * 版本内容
     */
    private String versionContent;

    private static final long serialVersionUID = 1L;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionTitle() {
        return versionTitle;
    }

    public void setVersionTitle(String versionTitle) {
        this.versionTitle = versionTitle;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getIsForce() {
        return isForce;
    }

    public void setIsForce(Boolean isForce) {
        this.isForce = isForce;
    }

    public String getVersionContent() {
        return versionContent;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent;
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
        Version other = (Version) that;
        return (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
            && (this.getVersionNumber() == null ? other.getVersionNumber() == null : this.getVersionNumber().equals(other.getVersionNumber()))
            && (this.getVersionTitle() == null ? other.getVersionTitle() == null : this.getVersionTitle().equals(other.getVersionTitle()))
            && (this.getApkUrl() == null ? other.getApkUrl() == null : this.getApkUrl().equals(other.getApkUrl()))
            && (this.getCreationDate() == null ? other.getCreationDate() == null : this.getCreationDate().equals(other.getCreationDate()))
            && (this.getIsForce() == null ? other.getIsForce() == null : this.getIsForce().equals(other.getIsForce()))
            && (this.getVersionContent() == null ? other.getVersionContent() == null : this.getVersionContent().equals(other.getVersionContent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVersionId() == null) ? 0 : getVersionId().hashCode());
        result = prime * result + ((getVersionNumber() == null) ? 0 : getVersionNumber().hashCode());
        result = prime * result + ((getVersionTitle() == null) ? 0 : getVersionTitle().hashCode());
        result = prime * result + ((getApkUrl() == null) ? 0 : getApkUrl().hashCode());
        result = prime * result + ((getCreationDate() == null) ? 0 : getCreationDate().hashCode());
        result = prime * result + ((getIsForce() == null) ? 0 : getIsForce().hashCode());
        result = prime * result + ((getVersionContent() == null) ? 0 : getVersionContent().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", versionId=").append(versionId);
        sb.append(", versionNumber=").append(versionNumber);
        sb.append(", versionTitle=").append(versionTitle);
        sb.append(", apkUrl=").append(apkUrl);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", isForce=").append(isForce);
        sb.append(", versionContent=").append(versionContent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}