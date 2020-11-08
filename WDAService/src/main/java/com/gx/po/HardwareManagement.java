package com.gx.po;

import java.io.Serializable;
import java.util.Date;

/**
 * hardware_management
 * @author 
 */
public class HardwareManagement implements Serializable {
    /**
     * 硬件管理ID
     */
    private Integer hardwareManagementId;

    /**
     * 最近使用用户ID
     */
    private Integer userId;

    /**
     * 硬件设备ID
     */
    private String hardwareEquipmentId;

    /**
     * 硬件设备名称
     */
    private String hardwareEquipmentName;

    /**
     * 使用日期
     */
    private Date useDate;

    /**
     * 使用区域
     */
    private String useArea;

    /**
     * 保管人
     */
    private String preserver;

    /**
     * 是否启用
     */
    private Boolean isDisabled;

    private static final long serialVersionUID = 1L;

    public Integer getHardwareManagementId() {
        return hardwareManagementId;
    }

    public void setHardwareManagementId(Integer hardwareManagementId) {
        this.hardwareManagementId = hardwareManagementId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHardwareEquipmentId() {
        return hardwareEquipmentId;
    }

    public void setHardwareEquipmentId(String hardwareEquipmentId) {
        this.hardwareEquipmentId = hardwareEquipmentId;
    }

    public String getHardwareEquipmentName() {
        return hardwareEquipmentName;
    }

    public void setHardwareEquipmentName(String hardwareEquipmentName) {
        this.hardwareEquipmentName = hardwareEquipmentName;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getUseArea() {
        return useArea;
    }

    public void setUseArea(String useArea) {
        this.useArea = useArea;
    }

    public String getPreserver() {
        return preserver;
    }

    public void setPreserver(String preserver) {
        this.preserver = preserver;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
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
        HardwareManagement other = (HardwareManagement) that;
        return (this.getHardwareManagementId() == null ? other.getHardwareManagementId() == null : this.getHardwareManagementId().equals(other.getHardwareManagementId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getHardwareEquipmentId() == null ? other.getHardwareEquipmentId() == null : this.getHardwareEquipmentId().equals(other.getHardwareEquipmentId()))
            && (this.getHardwareEquipmentName() == null ? other.getHardwareEquipmentName() == null : this.getHardwareEquipmentName().equals(other.getHardwareEquipmentName()))
            && (this.getUseDate() == null ? other.getUseDate() == null : this.getUseDate().equals(other.getUseDate()))
            && (this.getUseArea() == null ? other.getUseArea() == null : this.getUseArea().equals(other.getUseArea()))
            && (this.getPreserver() == null ? other.getPreserver() == null : this.getPreserver().equals(other.getPreserver()))
            && (this.getIsDisabled() == null ? other.getIsDisabled() == null : this.getIsDisabled().equals(other.getIsDisabled()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHardwareManagementId() == null) ? 0 : getHardwareManagementId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getHardwareEquipmentId() == null) ? 0 : getHardwareEquipmentId().hashCode());
        result = prime * result + ((getHardwareEquipmentName() == null) ? 0 : getHardwareEquipmentName().hashCode());
        result = prime * result + ((getUseDate() == null) ? 0 : getUseDate().hashCode());
        result = prime * result + ((getUseArea() == null) ? 0 : getUseArea().hashCode());
        result = prime * result + ((getPreserver() == null) ? 0 : getPreserver().hashCode());
        result = prime * result + ((getIsDisabled() == null) ? 0 : getIsDisabled().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hardwareManagementId=").append(hardwareManagementId);
        sb.append(", userId=").append(userId);
        sb.append(", hardwareEquipmentId=").append(hardwareEquipmentId);
        sb.append(", hardwareEquipmentName=").append(hardwareEquipmentName);
        sb.append(", useDate=").append(useDate);
        sb.append(", useArea=").append(useArea);
        sb.append(", preserver=").append(preserver);
        sb.append(", isDisabled=").append(isDisabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}