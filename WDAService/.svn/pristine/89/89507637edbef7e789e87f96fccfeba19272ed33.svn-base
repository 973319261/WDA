package com.gx.po;

import java.io.Serializable;

/**
 * harness_configuration
 * @author 
 */
public class HarnessConfiguration implements Serializable {
    /**
     * 线束段配置ID
     */
    private Integer harnessConfigurationId;

    /**
     * 车型ID
     */
    private Integer vehicleId;

    /**
     * 配置等级ID
     */
    private Integer configurationLevelId;

    /**
     * CAN配置ID
     */
    private Integer canConfigurationId;

    /**
     * 线束段
     */
    private String harness;

    private static final long serialVersionUID = 1L;

    public Integer getHarnessConfigurationId() {
        return harnessConfigurationId;
    }

    public void setHarnessConfigurationId(Integer harnessConfigurationId) {
        this.harnessConfigurationId = harnessConfigurationId;
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

    public Integer getCanConfigurationId() {
        return canConfigurationId;
    }

    public void setCanConfigurationId(Integer canConfigurationId) {
        this.canConfigurationId = canConfigurationId;
    }

    public String getHarness() {
        return harness;
    }

    public void setHarness(String harness) {
        this.harness = harness;
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
        HarnessConfiguration other = (HarnessConfiguration) that;
        return (this.getHarnessConfigurationId() == null ? other.getHarnessConfigurationId() == null : this.getHarnessConfigurationId().equals(other.getHarnessConfigurationId()))
            && (this.getVehicleId() == null ? other.getVehicleId() == null : this.getVehicleId().equals(other.getVehicleId()))
            && (this.getConfigurationLevelId() == null ? other.getConfigurationLevelId() == null : this.getConfigurationLevelId().equals(other.getConfigurationLevelId()))
            && (this.getCanConfigurationId() == null ? other.getCanConfigurationId() == null : this.getCanConfigurationId().equals(other.getCanConfigurationId()))
            && (this.getHarness() == null ? other.getHarness() == null : this.getHarness().equals(other.getHarness()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHarnessConfigurationId() == null) ? 0 : getHarnessConfigurationId().hashCode());
        result = prime * result + ((getVehicleId() == null) ? 0 : getVehicleId().hashCode());
        result = prime * result + ((getConfigurationLevelId() == null) ? 0 : getConfigurationLevelId().hashCode());
        result = prime * result + ((getCanConfigurationId() == null) ? 0 : getCanConfigurationId().hashCode());
        result = prime * result + ((getHarness() == null) ? 0 : getHarness().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", harnessConfigurationId=").append(harnessConfigurationId);
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", configurationLevelId=").append(configurationLevelId);
        sb.append(", canConfigurationId=").append(canConfigurationId);
        sb.append(", harness=").append(harness);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}