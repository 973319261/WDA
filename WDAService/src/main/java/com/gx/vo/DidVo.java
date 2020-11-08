package com.gx.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class DidVo implements Serializable {
    /**
     * didID
     */
    private Integer didId;

    /**
     * 关联ID
     */
    private Integer relevanceId;

    /**
     * DID名称
     */
    private String didName;

    /**
     * 标识符
     */
    private String identifier;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型ID(0代表DID,1代表快照)
     */
    private Integer typeId;
    
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
    
    /**
     * 车型名称
     */
    private String vehicleName;
    
    /**
     * 配置等级名称
     */
    private String configurationLevelName;
    
    /**
     * 模块简称
     */
    private String moduleName;
    
    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 最小值
     */
    private String min;

    /**
     * 最大值
     */
    private String max;

    /**
     * 单位
     */
    private String unit;
    
    /**
     * 信号类型ID
     */
    private Integer signalTypeId;

    /**
     * 原始值类型
     */
    private Integer rawValueType;

    /**
     * 开始位位置
     */
    private Integer startBitPosition;

    /**
     * 开始字节数
     */
    private Integer startByte;

    /**
     * 开始位数
     */
    private Integer startBit;

    /**
     * 长度位数
     */
    private Integer lengthBit;

    /**
     * 长度字节数
     */
    private Integer lengthByte;

    /**
     * 格式
     */
    private String format;
    
    /**
     * 缩放类型ID(0:None,1:Linear mX+B)
     */
    private Integer scalingTypeId;

    /**
     * 工程值
     */
    private BigDecimal engineeringValue;

    /**
     * 原始值
     */
    private BigDecimal rawValue;
    
    /**
     * DID类型ID
     */
    private Integer didTypeId;

    private static final long serialVersionUID = 1L;

	public Integer getDidId() {
		return didId;
	}

	public void setDidId(Integer didId) {
		this.didId = didId;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getConfigurationLevelName() {
		return configurationLevelName;
	}

	public void setConfigurationLevelName(String configurationLevelName) {
		this.configurationLevelName = configurationLevelName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getSignalTypeId() {
		return signalTypeId;
	}

	public Integer getRawValueType() {
		return rawValueType;
	}

	public void setRawValueType(Integer rawValueType) {
		this.rawValueType = rawValueType;
	}

	public Integer getStartBitPosition() {
		return startBitPosition;
	}

	public void setStartBitPosition(Integer startBitPosition) {
		this.startBitPosition = startBitPosition;
	}

	public Integer getStartByte() {
		return startByte;
	}

	public void setStartByte(Integer startByte) {
		this.startByte = startByte;
	}

	public Integer getStartBit() {
		return startBit;
	}

	public void setStartBit(Integer startBit) {
		this.startBit = startBit;
	}

	public Integer getLengthBit() {
		return lengthBit;
	}

	public void setLengthBit(Integer lengthBit) {
		this.lengthBit = lengthBit;
	}

	public Integer getLengthByte() {
		return lengthByte;
	}

	public void setLengthByte(Integer lengthByte) {
		this.lengthByte = lengthByte;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getScalingTypeId() {
		return scalingTypeId;
	}

	public void setScalingTypeId(Integer scalingTypeId) {
		this.scalingTypeId = scalingTypeId;
	}

	public BigDecimal getEngineeringValue() {
		return engineeringValue;
	}

	public void setEngineeringValue(BigDecimal engineeringValue) {
		this.engineeringValue = engineeringValue;
	}

	public BigDecimal getRawValue() {
		return rawValue;
	}

	public void setRawValue(BigDecimal rawValue) {
		this.rawValue = rawValue;
	}

	public void setSignalTypeId(Integer signalTypeId) {
		this.signalTypeId = signalTypeId;
	}

	public Integer getDidTypeId() {
		return didTypeId;
	}

	public void setDidTypeId(Integer didTypeId) {
		this.didTypeId = didTypeId;
	}
    
}
