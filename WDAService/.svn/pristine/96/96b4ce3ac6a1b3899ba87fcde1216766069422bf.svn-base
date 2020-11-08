package com.gx.po;

import java.io.Serializable;

/**
 * state_description
 * @author 
 */
public class StateDescription implements Serializable {
    /**
     * 状态描述ID
     */
    private Integer stateDescriptionId;

    /**
     * DID类型ID
     */
    private Integer didTypeId;

    /**
     * 十进制
     */
    private String decimals;

    /**
     * 十六进制
     */
    private String hexs;

    /**
     * 状态描述
     */
    private String stateDescription;

    private static final long serialVersionUID = 1L;

    public Integer getStateDescriptionId() {
        return stateDescriptionId;
    }

    public void setStateDescriptionId(Integer stateDescriptionId) {
        this.stateDescriptionId = stateDescriptionId;
    }

    public Integer getDidTypeId() {
        return didTypeId;
    }

    public void setDidTypeId(Integer didTypeId) {
        this.didTypeId = didTypeId;
    }

    public String getDecimals() {
        return decimals;
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getHexs() {
        return hexs;
    }

    public void setHexs(String hexs) {
        this.hexs = hexs;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
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
        StateDescription other = (StateDescription) that;
        return (this.getStateDescriptionId() == null ? other.getStateDescriptionId() == null : this.getStateDescriptionId().equals(other.getStateDescriptionId()))
            && (this.getDidTypeId() == null ? other.getDidTypeId() == null : this.getDidTypeId().equals(other.getDidTypeId()))
            && (this.getDecimals() == null ? other.getDecimals() == null : this.getDecimals().equals(other.getDecimals()))
            && (this.getHexs() == null ? other.getHexs() == null : this.getHexs().equals(other.getHexs()))
            && (this.getStateDescription() == null ? other.getStateDescription() == null : this.getStateDescription().equals(other.getStateDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStateDescriptionId() == null) ? 0 : getStateDescriptionId().hashCode());
        result = prime * result + ((getDidTypeId() == null) ? 0 : getDidTypeId().hashCode());
        result = prime * result + ((getDecimals() == null) ? 0 : getDecimals().hashCode());
        result = prime * result + ((getHexs() == null) ? 0 : getHexs().hashCode());
        result = prime * result + ((getStateDescription() == null) ? 0 : getStateDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stateDescriptionId=").append(stateDescriptionId);
        sb.append(", didTypeId=").append(didTypeId);
        sb.append(", decimals=").append(decimals);
        sb.append(", hexs=").append(hexs);
        sb.append(", stateDescription=").append(stateDescription);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}