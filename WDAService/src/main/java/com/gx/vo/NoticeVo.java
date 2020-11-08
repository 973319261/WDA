package com.gx.vo;

import java.io.Serializable;
import java.util.Date;

public class NoticeVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 /**
     * 公告ID
     */
    private Integer noticeId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 公告名称
     */
    private String noticeName;

    /**
     * 公告描述
     */
    private String noticeDescribe;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 访问量
     */
    private Integer visitorVolume;

	/**
	 * 用户姓名
	 */
	private String userName;
	
    /**
     * 文件类型ID
     */
    private Integer fileTypeId;

    /**
     * 文件类型名称
     */
    private String fileTypeName;
    /**
     * 文件ID
     */
    private Integer fileId;

    /**
     * 文件路径
     */
    private String fileName;
    
    private String adminName;
    
    private Integer adminId;

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getNoticeDescribe() {
		return noticeDescribe;
	}

	public void setNoticeDescribe(String noticeDescribe) {
		this.noticeDescribe = noticeDescribe;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Integer getVisitorVolume() {
		return visitorVolume;
	}

	public void setVisitorVolume(Integer visitorVolume) {
		this.visitorVolume = visitorVolume;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
