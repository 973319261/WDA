package com.gx.po;

import java.io.Serializable;

/**
 * did_conversion
 * @author 
 */
public class DidConversion implements Serializable {
    /**
     * DID转换ID
     */
    private Integer didConversionId;

    /**
     * DID名称
     */
    private String didName;

    /**
     * DID描述
     */
    private String didDescription;

    /**
     * DID类型
     */
    private String didType;

    private static final long serialVersionUID = 1L;

    public Integer getDidConversionId() {
        return didConversionId;
    }

    public void setDidConversionId(Integer didConversionId) {
        this.didConversionId = didConversionId;
    }

    public String getDidName() {
        return didName;
    }

    public void setDidName(String didName) {
        this.didName = didName;
    }

    public String getDidDescription() {
        return didDescription;
    }

    public void setDidDescription(String didDescription) {
        this.didDescription = didDescription;
    }

    public String getDidType() {
        return didType;
    }

    public void setDidType(String didType) {
        this.didType = didType;
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
        DidConversion other = (DidConversion) that;
        return (this.getDidConversionId() == null ? other.getDidConversionId() == null : this.getDidConversionId().equals(other.getDidConversionId()))
            && (this.getDidName() == null ? other.getDidName() == null : this.getDidName().equals(other.getDidName()))
            && (this.getDidDescription() == null ? other.getDidDescription() == null : this.getDidDescription().equals(other.getDidDescription()))
            && (this.getDidType() == null ? other.getDidType() == null : this.getDidType().equals(other.getDidType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDidConversionId() == null) ? 0 : getDidConversionId().hashCode());
        result = prime * result + ((getDidName() == null) ? 0 : getDidName().hashCode());
        result = prime * result + ((getDidDescription() == null) ? 0 : getDidDescription().hashCode());
        result = prime * result + ((getDidType() == null) ? 0 : getDidType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", didConversionId=").append(didConversionId);
        sb.append(", didName=").append(didName);
        sb.append(", didDescription=").append(didDescription);
        sb.append(", didType=").append(didType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}