package com.gx.wda.bean;


/**
 * 故障码VO
 * @author LJ
 *
 */
public class FaultCodeVo {
	  /**
     * 车型ID
     */
    private Integer vehicleId;

    /**
     * 车型名称
     */
    private String vehicleName;
    /**
     * 配置等级ID
     */
    private Integer configurationLevelId;

    /**
     * 配置等级名称
     */
    private String configurationLevelName;
    /**
     * 模块ID
     */
    private Integer moduleId;

    /**
     * 模块简称
     */
    private String moduleName;
    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;
    
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

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

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

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

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
	        FaultCodeVo other = (FaultCodeVo) that;
	        return (this.getFaultCodeId() == null ? other.getFaultCodeId() == null : this.getFaultCodeId().equals(other.getFaultCodeId()))
	            && (this.getVehicleId() == null ? other.getVehicleId() == null : this.getVehicleId().equals(other.getVehicleId()))
	            && (this.getConfigurationLevelId() == null ? other.getConfigurationLevelId() == null : this.getConfigurationLevelId().equals(other.getConfigurationLevelId()))
	            && (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
	            && (this.getSupplierId() == null ? other.getSupplierId() == null : this.getSupplierId().equals(other.getSupplierId()));
	    }
	
}
