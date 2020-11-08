package com.gx.po;

import java.io.Serializable;

/**
 * fault_code
 * @author 
 */
public class FaultCode implements Serializable {
    /**
     * 故障码ID
     */
    private Integer faultCodeId;

    /**
     * 正常故障码
     */
    private String dtc;

    /**
     * 十六进制故障码
     */
    private String hexDtc;

    /**
     * 中文描述
     */
    private String chineseDescription;

    /**
     * 英文描述
     */
    private String englishDescription;

    /**
     * 运行条件
     */
    private String operatingConditions;

    /**
     * 设置条件
     */
    private String settingConditions;

    /**
     * 设置时发生的操作
     */
    private String settingAfterConditions;

    /**
     * 恢复条件
     */
    private String restoreConditions;

    /**
     * 激近故障灯原则
     */
    private String activateMilRegulations;

    /**
     * 熄灭故障灯原则
     */
    private String milOffRegulations;

    /**
     * 清除故障码条件
     */
    private String clearConditions;

    /**
     * 访问量
     */
    private Integer visitorVolume;

    private static final long serialVersionUID = 1L;

    public Integer getFaultCodeId() {
        return faultCodeId;
    }

    public void setFaultCodeId(Integer faultCodeId) {
        this.faultCodeId = faultCodeId;
    }

    public String getDtc() {
        return dtc;
    }

    public void setDtc(String dtc) {
        this.dtc = dtc;
    }

    public String getHexDtc() {
        return hexDtc;
    }

    public void setHexDtc(String hexDtc) {
        this.hexDtc = hexDtc;
    }

    public String getChineseDescription() {
        return chineseDescription;
    }

    public void setChineseDescription(String chineseDescription) {
        this.chineseDescription = chineseDescription;
    }

    public String getEnglishDescription() {
        return englishDescription;
    }

    public void setEnglishDescription(String englishDescription) {
        this.englishDescription = englishDescription;
    }

    public String getOperatingConditions() {
        return operatingConditions;
    }

    public void setOperatingConditions(String operatingConditions) {
        this.operatingConditions = operatingConditions;
    }

    public String getSettingConditions() {
        return settingConditions;
    }

    public void setSettingConditions(String settingConditions) {
        this.settingConditions = settingConditions;
    }

    public String getSettingAfterConditions() {
        return settingAfterConditions;
    }

    public void setSettingAfterConditions(String settingAfterConditions) {
        this.settingAfterConditions = settingAfterConditions;
    }

    public String getRestoreConditions() {
        return restoreConditions;
    }

    public void setRestoreConditions(String restoreConditions) {
        this.restoreConditions = restoreConditions;
    }

    public String getActivateMilRegulations() {
        return activateMilRegulations;
    }

    public void setActivateMilRegulations(String activateMilRegulations) {
        this.activateMilRegulations = activateMilRegulations;
    }

    public String getMilOffRegulations() {
        return milOffRegulations;
    }

    public void setMilOffRegulations(String milOffRegulations) {
        this.milOffRegulations = milOffRegulations;
    }

    public String getClearConditions() {
        return clearConditions;
    }

    public void setClearConditions(String clearConditions) {
        this.clearConditions = clearConditions;
    }

    public Integer getVisitorVolume() {
        return visitorVolume;
    }

    public void setVisitorVolume(Integer visitorVolume) {
        this.visitorVolume = visitorVolume;
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
        FaultCode other = (FaultCode) that;
        return (this.getFaultCodeId() == null ? other.getFaultCodeId() == null : this.getFaultCodeId().equals(other.getFaultCodeId()))
            && (this.getDtc() == null ? other.getDtc() == null : this.getDtc().equals(other.getDtc()))
            && (this.getHexDtc() == null ? other.getHexDtc() == null : this.getHexDtc().equals(other.getHexDtc()))
            && (this.getChineseDescription() == null ? other.getChineseDescription() == null : this.getChineseDescription().equals(other.getChineseDescription()))
            && (this.getEnglishDescription() == null ? other.getEnglishDescription() == null : this.getEnglishDescription().equals(other.getEnglishDescription()))
            && (this.getOperatingConditions() == null ? other.getOperatingConditions() == null : this.getOperatingConditions().equals(other.getOperatingConditions()))
            && (this.getSettingConditions() == null ? other.getSettingConditions() == null : this.getSettingConditions().equals(other.getSettingConditions()))
            && (this.getSettingAfterConditions() == null ? other.getSettingAfterConditions() == null : this.getSettingAfterConditions().equals(other.getSettingAfterConditions()))
            && (this.getRestoreConditions() == null ? other.getRestoreConditions() == null : this.getRestoreConditions().equals(other.getRestoreConditions()))
            && (this.getActivateMilRegulations() == null ? other.getActivateMilRegulations() == null : this.getActivateMilRegulations().equals(other.getActivateMilRegulations()))
            && (this.getMilOffRegulations() == null ? other.getMilOffRegulations() == null : this.getMilOffRegulations().equals(other.getMilOffRegulations()))
            && (this.getClearConditions() == null ? other.getClearConditions() == null : this.getClearConditions().equals(other.getClearConditions()))
            && (this.getVisitorVolume() == null ? other.getVisitorVolume() == null : this.getVisitorVolume().equals(other.getVisitorVolume()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFaultCodeId() == null) ? 0 : getFaultCodeId().hashCode());
        result = prime * result + ((getDtc() == null) ? 0 : getDtc().hashCode());
        result = prime * result + ((getHexDtc() == null) ? 0 : getHexDtc().hashCode());
        result = prime * result + ((getChineseDescription() == null) ? 0 : getChineseDescription().hashCode());
        result = prime * result + ((getEnglishDescription() == null) ? 0 : getEnglishDescription().hashCode());
        result = prime * result + ((getOperatingConditions() == null) ? 0 : getOperatingConditions().hashCode());
        result = prime * result + ((getSettingConditions() == null) ? 0 : getSettingConditions().hashCode());
        result = prime * result + ((getSettingAfterConditions() == null) ? 0 : getSettingAfterConditions().hashCode());
        result = prime * result + ((getRestoreConditions() == null) ? 0 : getRestoreConditions().hashCode());
        result = prime * result + ((getActivateMilRegulations() == null) ? 0 : getActivateMilRegulations().hashCode());
        result = prime * result + ((getMilOffRegulations() == null) ? 0 : getMilOffRegulations().hashCode());
        result = prime * result + ((getClearConditions() == null) ? 0 : getClearConditions().hashCode());
        result = prime * result + ((getVisitorVolume() == null) ? 0 : getVisitorVolume().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", faultCodeId=").append(faultCodeId);
        sb.append(", dtc=").append(dtc);
        sb.append(", hexDtc=").append(hexDtc);
        sb.append(", chineseDescription=").append(chineseDescription);
        sb.append(", englishDescription=").append(englishDescription);
        sb.append(", operatingConditions=").append(operatingConditions);
        sb.append(", settingConditions=").append(settingConditions);
        sb.append(", settingAfterConditions=").append(settingAfterConditions);
        sb.append(", restoreConditions=").append(restoreConditions);
        sb.append(", activateMilRegulations=").append(activateMilRegulations);
        sb.append(", milOffRegulations=").append(milOffRegulations);
        sb.append(", clearConditions=").append(clearConditions);
        sb.append(", visitorVolume=").append(visitorVolume);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}