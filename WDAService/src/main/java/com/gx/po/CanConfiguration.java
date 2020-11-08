package com.gx.po;

import java.io.Serializable;

/**
 * can_configuration
 * @author 
 */
public class CanConfiguration implements Serializable {
    /**
     * CAN配置ID
     */
    private Integer canConfigurationId;

    /**
     * CAN通道
     */
    private String canPassage;

    /**
     * CAN值
     */
    private String canValue;

    /**
     * CAN描述
     */
    private String canDescription;

    /**
     * DID名称
     */
    private String didName;

    /**
     * 映射类型ID(永久和临时)
     */
    private Integer mapTypeId;

    private static final long serialVersionUID = 1L;

    public Integer getCanConfigurationId() {
        return canConfigurationId;
    }

    public void setCanConfigurationId(Integer canConfigurationId) {
        this.canConfigurationId = canConfigurationId;
    }

    public String getCanPassage() {
        return canPassage;
    }

    public void setCanPassage(String canPassage) {
        this.canPassage = canPassage;
    }

    public String getCanValue() {
        return canValue;
    }

    public void setCanValue(String canValue) {
        this.canValue = canValue;
    }

    public String getCanDescription() {
        return canDescription;
    }

    public void setCanDescription(String canDescription) {
        this.canDescription = canDescription;
    }

    public String getDidName() {
        return didName;
    }

    public void setDidName(String didName) {
        this.didName = didName;
    }

    public Integer getMapTypeId() {
        return mapTypeId;
    }

    public void setMapTypeId(Integer mapTypeId) {
        this.mapTypeId = mapTypeId;
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
        CanConfiguration other = (CanConfiguration) that;
        return (this.getCanConfigurationId() == null ? other.getCanConfigurationId() == null : this.getCanConfigurationId().equals(other.getCanConfigurationId()))
            && (this.getCanPassage() == null ? other.getCanPassage() == null : this.getCanPassage().equals(other.getCanPassage()))
            && (this.getCanValue() == null ? other.getCanValue() == null : this.getCanValue().equals(other.getCanValue()))
            && (this.getCanDescription() == null ? other.getCanDescription() == null : this.getCanDescription().equals(other.getCanDescription()))
            && (this.getDidName() == null ? other.getDidName() == null : this.getDidName().equals(other.getDidName()))
            && (this.getMapTypeId() == null ? other.getMapTypeId() == null : this.getMapTypeId().equals(other.getMapTypeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCanConfigurationId() == null) ? 0 : getCanConfigurationId().hashCode());
        result = prime * result + ((getCanPassage() == null) ? 0 : getCanPassage().hashCode());
        result = prime * result + ((getCanValue() == null) ? 0 : getCanValue().hashCode());
        result = prime * result + ((getCanDescription() == null) ? 0 : getCanDescription().hashCode());
        result = prime * result + ((getDidName() == null) ? 0 : getDidName().hashCode());
        result = prime * result + ((getMapTypeId() == null) ? 0 : getMapTypeId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", canConfigurationId=").append(canConfigurationId);
        sb.append(", canPassage=").append(canPassage);
        sb.append(", canValue=").append(canValue);
        sb.append(", canDescription=").append(canDescription);
        sb.append(", didName=").append(didName);
        sb.append(", mapTypeId=").append(mapTypeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}