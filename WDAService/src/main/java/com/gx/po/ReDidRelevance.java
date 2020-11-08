package com.gx.po;

import java.io.Serializable;

/**
 * re_did_relevance
 * @author 
 */
public class ReDidRelevance implements Serializable {
    /**
     * DID关联ID
     */
    private Integer reDidRelevanceId;

    /**
     * DID转换ID
     */
    private Integer didConversionId;

    /**
     * 关联ID
     */
    private Integer relevanceId;

    private static final long serialVersionUID = 1L;

    public Integer getReDidRelevanceId() {
        return reDidRelevanceId;
    }

    public void setReDidRelevanceId(Integer reDidRelevanceId) {
        this.reDidRelevanceId = reDidRelevanceId;
    }

    public Integer getDidConversionId() {
        return didConversionId;
    }

    public void setDidConversionId(Integer didConversionId) {
        this.didConversionId = didConversionId;
    }

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
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
        ReDidRelevance other = (ReDidRelevance) that;
        return (this.getReDidRelevanceId() == null ? other.getReDidRelevanceId() == null : this.getReDidRelevanceId().equals(other.getReDidRelevanceId()))
            && (this.getDidConversionId() == null ? other.getDidConversionId() == null : this.getDidConversionId().equals(other.getDidConversionId()))
            && (this.getRelevanceId() == null ? other.getRelevanceId() == null : this.getRelevanceId().equals(other.getRelevanceId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReDidRelevanceId() == null) ? 0 : getReDidRelevanceId().hashCode());
        result = prime * result + ((getDidConversionId() == null) ? 0 : getDidConversionId().hashCode());
        result = prime * result + ((getRelevanceId() == null) ? 0 : getRelevanceId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reDidRelevanceId=").append(reDidRelevanceId);
        sb.append(", didConversionId=").append(didConversionId);
        sb.append(", relevanceId=").append(relevanceId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}