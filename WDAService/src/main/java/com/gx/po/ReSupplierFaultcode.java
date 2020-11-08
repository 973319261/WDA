package com.gx.po;

import java.io.Serializable;

/**
 * re_supplier_faultcode
 * @author 
 */
public class ReSupplierFaultcode implements Serializable {
    /**
     * 关联ID
     */
    private Integer reId;

    /**
     * 关联ID
     */
    private Integer relevanceId;

    /**
     * 故障码ID
     */
    private Integer faultCodeId;

    private static final long serialVersionUID = 1L;

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    public Integer getFaultCodeId() {
        return faultCodeId;
    }

    public void setFaultCodeId(Integer faultCodeId) {
        this.faultCodeId = faultCodeId;
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
        ReSupplierFaultcode other = (ReSupplierFaultcode) that;
        return (this.getReId() == null ? other.getReId() == null : this.getReId().equals(other.getReId()))
            && (this.getRelevanceId() == null ? other.getRelevanceId() == null : this.getRelevanceId().equals(other.getRelevanceId()))
            && (this.getFaultCodeId() == null ? other.getFaultCodeId() == null : this.getFaultCodeId().equals(other.getFaultCodeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReId() == null) ? 0 : getReId().hashCode());
        result = prime * result + ((getRelevanceId() == null) ? 0 : getRelevanceId().hashCode());
        result = prime * result + ((getFaultCodeId() == null) ? 0 : getFaultCodeId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reId=").append(reId);
        sb.append(", relevanceId=").append(relevanceId);
        sb.append(", faultCodeId=").append(faultCodeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}