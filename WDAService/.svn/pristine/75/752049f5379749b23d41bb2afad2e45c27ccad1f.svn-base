package com.gx.vo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HardwareVo implements Serializable {
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");
	
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
    
    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户名称
     */
    private String userName;

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
		if(useDate!=null) {
			
		}else {
			try {
				useDate=dateFormat.parse("2000-01-01");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
