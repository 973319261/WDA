package com.gx.po;

import java.io.Serializable;

/**
 * jurisdiction_detail
 * @author 
 */
public class JurisdictionDetail implements Serializable {
    /**
     * 权限明细ID
     */
    private Integer jurisdictionDetailId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer jurisdictionId;

    /**
     * 是否启用
     */
    private Boolean isEnable;

    private static final long serialVersionUID = 1L;

    public Integer getJurisdictionDetailId() {
        return jurisdictionDetailId;
    }

    public void setJurisdictionDetailId(Integer jurisdictionDetailId) {
        this.jurisdictionDetailId = jurisdictionDetailId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getJurisdictionId() {
        return jurisdictionId;
    }

    public void setJurisdictionId(Integer jurisdictionId) {
        this.jurisdictionId = jurisdictionId;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
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
        JurisdictionDetail other = (JurisdictionDetail) that;
        return (this.getJurisdictionDetailId() == null ? other.getJurisdictionDetailId() == null : this.getJurisdictionDetailId().equals(other.getJurisdictionDetailId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getJurisdictionId() == null ? other.getJurisdictionId() == null : this.getJurisdictionId().equals(other.getJurisdictionId()))
            && (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getJurisdictionDetailId() == null) ? 0 : getJurisdictionDetailId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getJurisdictionId() == null) ? 0 : getJurisdictionId().hashCode());
        result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", jurisdictionDetailId=").append(jurisdictionDetailId);
        sb.append(", roleId=").append(roleId);
        sb.append(", jurisdictionId=").append(jurisdictionId);
        sb.append(", isEnable=").append(isEnable);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}