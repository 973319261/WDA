package com.gx.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class UserVo implements Serializable,Comparable<UserVo>{

	 /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 到期日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date expirationDate;

    /**
     * 用户用途
     */
    private String purpose;

    /**
     * 用户编号
     */
    private String userNumber;

    /**
     * 用户区域
     */
    private String area;

    /**
     * 故障码访问量
     */
    private Integer faultcodeVisit;

    /**
     * 分享访问量
     */
    private Integer shareVisit;

    /**
     * 算法访问量
     */
    private Integer arithmeticVisit;

    /**
     * 设备ID
     */
    private String androidId;

    /**
     * 是否过期
     */
    private Boolean isExpires;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDescribe;

    /**
     * 是否启用
     */
    private Boolean isEnable;
    /**
     * 是否在线
     */
    private Boolean isOnline;
	private static final long serialVersionUID = 1L;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getFaultcodeVisit() {
		return faultcodeVisit;
	}

	public void setFaultcodeVisit(Integer faultcodeVisit) {
		this.faultcodeVisit = faultcodeVisit;
	}

	public Integer getShareVisit() {
		return shareVisit;
	}

	public void setShareVisit(Integer shareVisit) {
		this.shareVisit = shareVisit;
	}

	public Integer getArithmeticVisit() {
		return arithmeticVisit;
	}

	public void setArithmeticVisit(Integer arithmeticVisit) {
		this.arithmeticVisit = arithmeticVisit;
	}

	public String getAndroidId() {
		return androidId;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	public Boolean getIsExpires() {
		return isExpires;
	}

	public void setIsExpires(Boolean isExpires) {
		this.isExpires = isExpires;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescribe() {
		return roleDescribe;
	}

	public void setRoleDescribe(String roleDescribe) {
		this.roleDescribe = roleDescribe;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	@Override
	//在线 离线排序
    public int compareTo(UserVo o) {
        int result = 0;
        //按照生产时间降序
        result = o.isOnline.compareTo(this.isOnline);
        return result;
    }
}
