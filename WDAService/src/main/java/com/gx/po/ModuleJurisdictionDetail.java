package com.gx.po;

import java.io.Serializable;

/**
 * module_jurisdiction_detail
 * @author 
 */
public class ModuleJurisdictionDetail implements Serializable {
    /**
     * 模块权明细限ID
     */
    private Integer moduleJurisdictionDetailId;

    /**
     * 模块ID
     */
    private Integer moduleId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 是否启用
     */
    private Boolean isEnable;

    private static final long serialVersionUID = 1L;

    public Integer getModuleJurisdictionDetailId() {
        return moduleJurisdictionDetailId;
    }

    public void setModuleJurisdictionDetailId(Integer moduleJurisdictionDetailId) {
        this.moduleJurisdictionDetailId = moduleJurisdictionDetailId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
        ModuleJurisdictionDetail other = (ModuleJurisdictionDetail) that;
        return (this.getModuleJurisdictionDetailId() == null ? other.getModuleJurisdictionDetailId() == null : this.getModuleJurisdictionDetailId().equals(other.getModuleJurisdictionDetailId()))
            && (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModuleJurisdictionDetailId() == null) ? 0 : getModuleJurisdictionDetailId().hashCode());
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", moduleJurisdictionDetailId=").append(moduleJurisdictionDetailId);
        sb.append(", moduleId=").append(moduleId);
        sb.append(", roleId=").append(roleId);
        sb.append(", isEnable=").append(isEnable);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}