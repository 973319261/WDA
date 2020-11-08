package com.gx.vo;

import java.io.Serializable;
import java.util.Date;

public class InformVo implements Serializable {
    /**
     * 通知ID
     */
    private Integer informId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 通知标题
     */
    private String informTitle;

    /**
     * 通知内容
     */
    private String informContent;

    /**
     * 创建时间
     */
    private Date creationTime;
    
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 是否已读
     */
    private Boolean isRead;
    /**
     * 类型ID
     */
    private Integer typeId;
    
    private String adminName;
    
    private Integer adminId;

    private static final long serialVersionUID = 1L;

    public Integer getInformId() {
        return informId;
    }

    public void setInformId(Integer informId) {
        this.informId = informId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInformTitle() {
        return informTitle;
    }

    public void setInformTitle(String informTitle) {
        this.informTitle = informTitle;
    }

    public String getInformContent() {
        return informContent;
    }

    public void setInformContent(String informContent) {
        this.informContent = informContent;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
    
}
