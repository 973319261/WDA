package com.gx.po;

import java.io.Serializable;

/**
 * re_arithmetic_supplier
 * @author 
 */
public class ReArithmeticSupplier implements Serializable {
    /**
     * 算法关联ID
     */
    private Integer relevanceArithmeticId;

    /**
     * 关联ID
     */
    private Integer relevanceId;

    /**
     * 算法ID
     */
    private Integer arithmeticId;

    private static final long serialVersionUID = 1L;

    public Integer getRelevanceArithmeticId() {
        return relevanceArithmeticId;
    }

    public void setRelevanceArithmeticId(Integer relevanceArithmeticId) {
        this.relevanceArithmeticId = relevanceArithmeticId;
    }

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    public Integer getArithmeticId() {
        return arithmeticId;
    }

    public void setArithmeticId(Integer arithmeticId) {
        this.arithmeticId = arithmeticId;
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
        ReArithmeticSupplier other = (ReArithmeticSupplier) that;
        return (this.getRelevanceArithmeticId() == null ? other.getRelevanceArithmeticId() == null : this.getRelevanceArithmeticId().equals(other.getRelevanceArithmeticId()))
            && (this.getRelevanceId() == null ? other.getRelevanceId() == null : this.getRelevanceId().equals(other.getRelevanceId()))
            && (this.getArithmeticId() == null ? other.getArithmeticId() == null : this.getArithmeticId().equals(other.getArithmeticId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRelevanceArithmeticId() == null) ? 0 : getRelevanceArithmeticId().hashCode());
        result = prime * result + ((getRelevanceId() == null) ? 0 : getRelevanceId().hashCode());
        result = prime * result + ((getArithmeticId() == null) ? 0 : getArithmeticId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", relevanceArithmeticId=").append(relevanceArithmeticId);
        sb.append(", relevanceId=").append(relevanceId);
        sb.append(", arithmeticId=").append(arithmeticId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}