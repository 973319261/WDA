package com.gx.po;

import java.io.Serializable;

/**
 * jurisdiction
 * @author 
 */
public class Jurisdiction implements Serializable {
    /**
     * 权限ID
     */
    private Integer jurisdictionId;

    /**
     * 权限名称
     */
    private String jurisdictionName;

    private static final long serialVersionUID = 1L;

    public Integer getJurisdictionId() {
        return jurisdictionId;
    }

    public void setJurisdictionId(Integer jurisdictionId) {
        this.jurisdictionId = jurisdictionId;
    }

    public String getJurisdictionName() {
        return jurisdictionName;
    }

    public void setJurisdictionName(String jurisdictionName) {
        this.jurisdictionName = jurisdictionName;
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
        Jurisdiction other = (Jurisdiction) that;
        return (this.getJurisdictionId() == null ? other.getJurisdictionId() == null : this.getJurisdictionId().equals(other.getJurisdictionId()))
            && (this.getJurisdictionName() == null ? other.getJurisdictionName() == null : this.getJurisdictionName().equals(other.getJurisdictionName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getJurisdictionId() == null) ? 0 : getJurisdictionId().hashCode());
        result = prime * result + ((getJurisdictionName() == null) ? 0 : getJurisdictionName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", jurisdictionId=").append(jurisdictionId);
        sb.append(", jurisdictionName=").append(jurisdictionName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}