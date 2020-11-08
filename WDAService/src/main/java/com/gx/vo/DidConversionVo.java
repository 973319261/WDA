package com.gx.vo;

import java.io.Serializable;

public class DidConversionVo implements Serializable {
    /**
     * DID关联ID
     */
    private Integer reDidRelevanceId;

    /**
     * DID转换ID
     */
    private Integer didConversionId;

    /**
     * 关联ID
     */
    private Integer relevanceId;
    
    /**
     * DID名称
     */
    private String didName;

    /**
     * DID描述
     */
    private String didDescription;

    /**
     * DID类型
     */
    private String didType;
    
    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 模块ID
     */
    private Integer moduleId;

    /**
     * 模块简称
     */
    private String moduleName;
    
    /**
     * 配置等级ID
     */
    private Integer configurationLevelId;

    /**
     * 配置等级名称
     */
    private String configurationLevelName;
    
    /**
     * 车型ID
     */
    private Integer vehicleId;

    /**
     * 车型名称
     */
    private String vehicleName;

    private static final long serialVersionUID = 1L;

	public Integer getReDidRelevanceId() {
		return reDidRelevanceId;
	}

	public void setReDidRelevanceId(Integer reDidRelevanceId) {
		this.reDidRelevanceId = reDidRelevanceId;
	}

	public Integer getDidConversionId() {
		return didConversionId;
	}

	public void setDidConversionId(Integer didConversionId) {
		this.didConversionId = didConversionId;
	}

	public Integer getRelevanceId() {
		return relevanceId;
	}

	public void setRelevanceId(Integer relevanceId) {
		this.relevanceId = relevanceId;
	}

	public String getDidName() {
		return didName;
	}

	public void setDidName(String didName) {
		this.didName = didName;
	}

	public String getDidDescription() {
		return didDescription;
	}

	public void setDidDescription(String didDescription) {
		this.didDescription = didDescription;
	}

	public String getDidType() {
		return didType;
	}

	public void setDidType(String didType) {
		this.didType = didType;
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
    
    
}
