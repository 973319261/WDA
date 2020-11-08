package com.gx.po;

import java.io.Serializable;

/**
 * configuration_level
 * @author 
 */
public class ConfigurationLevel implements Serializable {
    /**
     * 配置等级ID
     */
    private Integer configurationLevelId;

    /**
     * 配置等级名称
     */
    private String configurationLevelName;

    private static final long serialVersionUID = 1L;

    public Integer getConfigurationLevelId() {
        return configurationLevelId;
    }

    public void setConfigurationLevelId(Integer configurationLevelId) {
        this.configurationLevelId = configurationLevelId;
    }

    public String getConfigurationLevelName() {
        return configurationLevelName;
    }

    public void setConfigurationLevelName(String configurationLevelName) {
        this.configurationLevelName = configurationLevelName;
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
        ConfigurationLevel other = (ConfigurationLevel) that;
        return (this.getConfigurationLevelId() == null ? other.getConfigurationLevelId() == null : this.getConfigurationLevelId().equals(other.getConfigurationLevelId()))
            && (this.getConfigurationLevelName() == null ? other.getConfigurationLevelName() == null : this.getConfigurationLevelName().equals(other.getConfigurationLevelName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getConfigurationLevelId() == null) ? 0 : getConfigurationLevelId().hashCode());
        result = prime * result + ((getConfigurationLevelName() == null) ? 0 : getConfigurationLevelName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", configurationLevelId=").append(configurationLevelId);
        sb.append(", configurationLevelName=").append(configurationLevelName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}