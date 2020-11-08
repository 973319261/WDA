package com.gx.po;

import java.io.Serializable;

/**
 * did
 * @author 
 */
public class Did implements Serializable {
    /**
     * didID
     */
    private Integer didId;

    /**
     * 关联ID
     */
    private Integer relevanceId;

    /**
     * DID名称
     */
    private String didName;

    /**
     * 标识符
     */
    private String identifier;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型ID(0代表DID,1代表快照)
     */
    private Integer typeId;

    private static final long serialVersionUID = 1L;

    public Integer getDidId() {
        return didId;
    }

    public void setDidId(Integer didId) {
        this.didId = didId;
    }

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getDidName() {
        return didName;
    }

    public void setDidName(String didName) {
        this.didName = didName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
        Did other = (Did) that;
        return (this.getDidId() == null ? other.getDidId() == null : this.getDidId().equals(other.getDidId()))
            && (this.getRelevanceId() == null ? other.getRelevanceId() == null : this.getRelevanceId().equals(other.getRelevanceId()))
            && (this.getDidName() == null ? other.getDidName() == null : this.getDidName().equals(other.getDidName()))
            && (this.getIdentifier() == null ? other.getIdentifier() == null : this.getIdentifier().equals(other.getIdentifier()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDidId() == null) ? 0 : getDidId().hashCode());
        result = prime * result + ((getRelevanceId() == null) ? 0 : getRelevanceId().hashCode());
        result = prime * result + ((getDidName() == null) ? 0 : getDidName().hashCode());
        result = prime * result + ((getIdentifier() == null) ? 0 : getIdentifier().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", didId=").append(didId);
        sb.append(", relevanceId=").append(relevanceId);
        sb.append(", didName=").append(didName);
        sb.append(", identifier=").append(identifier);
        sb.append(", description=").append(description);
        sb.append(", typeId=").append(typeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}