package com.gx.po;

import java.io.Serializable;

/**
 * re_vehicle_supplier
 * @author 
 */
public class ReVehicleSupplier implements Serializable {
    /**
     * 关联ID
     */
    private Integer relevanceId;

    /**
     * 车型ID
     */
    private Integer vehicleId;

    /**
     * 配置等级ID
     */
    private Integer configurationLevelId;

    /**
     * 模块ID
     */
    private Integer moduleId;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    private static final long serialVersionUID = 1L;

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getConfigurationLevelId() {
        return configurationLevelId;
    }

    public void setConfigurationLevelId(Integer configurationLevelId) {
        this.configurationLevelId = configurationLevelId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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
        ReVehicleSupplier other = (ReVehicleSupplier) that;
        return (this.getRelevanceId() == null ? other.getRelevanceId() == null : this.getRelevanceId().equals(other.getRelevanceId()))
            && (this.getVehicleId() == null ? other.getVehicleId() == null : this.getVehicleId().equals(other.getVehicleId()))
            && (this.getConfigurationLevelId() == null ? other.getConfigurationLevelId() == null : this.getConfigurationLevelId().equals(other.getConfigurationLevelId()))
            && (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
            && (this.getSupplierId() == null ? other.getSupplierId() == null : this.getSupplierId().equals(other.getSupplierId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRelevanceId() == null) ? 0 : getRelevanceId().hashCode());
        result = prime * result + ((getVehicleId() == null) ? 0 : getVehicleId().hashCode());
        result = prime * result + ((getConfigurationLevelId() == null) ? 0 : getConfigurationLevelId().hashCode());
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        result = prime * result + ((getSupplierId() == null) ? 0 : getSupplierId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", relevanceId=").append(relevanceId);
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", configurationLevelId=").append(configurationLevelId);
        sb.append(", moduleId=").append(moduleId);
        sb.append(", supplierId=").append(supplierId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}