package com.gx.po;

import java.io.Serializable;

/**
 * module
 * @author 
 */
public class Module implements Serializable {
    /**
     * 模块ID
     */
    private Integer moduleId;

    /**
     * 模块简称
     */
    private String moduleName;

    /**
     * 模块全称
     */
    private String moduleFullName;

    /**
     * Canid值
     */
    private String canidValue;

    private static final long serialVersionUID = 1L;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleFullName() {
        return moduleFullName;
    }

    public void setModuleFullName(String moduleFullName) {
        this.moduleFullName = moduleFullName;
    }

    public String getCanidValue() {
        return canidValue;
    }

    public void setCanidValue(String canidValue) {
        this.canidValue = canidValue;
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
        Module other = (Module) that;
        return (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
            && (this.getModuleName() == null ? other.getModuleName() == null : this.getModuleName().equals(other.getModuleName()))
            && (this.getModuleFullName() == null ? other.getModuleFullName() == null : this.getModuleFullName().equals(other.getModuleFullName()))
            && (this.getCanidValue() == null ? other.getCanidValue() == null : this.getCanidValue().equals(other.getCanidValue()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        result = prime * result + ((getModuleName() == null) ? 0 : getModuleName().hashCode());
        result = prime * result + ((getModuleFullName() == null) ? 0 : getModuleFullName().hashCode());
        result = prime * result + ((getCanidValue() == null) ? 0 : getCanidValue().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", moduleId=").append(moduleId);
        sb.append(", moduleName=").append(moduleName);
        sb.append(", moduleFullName=").append(moduleFullName);
        sb.append(", canidValue=").append(canidValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}