package com.gx.vo;

import java.io.Serializable;

public class JurisDictionVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 权限明细ID
     */
    private Integer jurisdictionDetailId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer jurisdictionId;

    /**
     * 是否启用
     */
    private Boolean isEnable;
    
    /**
     * 权限名称
     */
    private String jurisdictionName;

	public Integer getJurisdictionDetailId() {
		return jurisdictionDetailId;
	}

	public void setJurisdictionDetailId(Integer jurisdictionDetailId) {
		this.jurisdictionDetailId = jurisdictionDetailId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getJurisdictionId() {
		return jurisdictionId;
	}

	public void setJurisdictionId(Integer jurisdictionId) {
		this.jurisdictionId = jurisdictionId;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getJurisdictionName() {
		return jurisdictionName;
	}

	public void setJurisdictionName(String jurisdictionName) {
		this.jurisdictionName = jurisdictionName;
	}

}
