package com.gx.wda.bean;

import java.io.Serializable;
import java.util.Date;


public class CommentsVo implements Serializable  {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 评论ID
	 */
	private Integer commentId;

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 公告ID
	 */
	private Integer noticeId;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 创建时间
	 */
	private Date creationTime;

	/**
	 * 回复评论ID
	 */
	private Integer replyId;

	/**
	 * 评论用户ID
	 */
	private Integer criticId;

	/**
	 * 管理员ID
	 */
	private Integer adminId;

	/**
	 * 回复对象的类型(0代表用户，1代表管理员)
	 */
	private Integer commentType;

	/**
	 * 层级类型(区分二级评论)
	 */
	private Integer tierType;

	/**
	 * 管理员名称
	 */
	private String adminName;

	//额外字段
	//userName
	private String userName;
	private String cirticName;
	//下级评论总数
	private Integer replyCount;
	private Integer pageSize;
	private Integer currentPage;
	private Integer startIndex;


	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getCommentType() {
		return commentType;
	}

	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}

	public Integer getTierType() {
		return tierType;
	}

	public void setTierType(Integer tierType) {
		this.tierType = tierType;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public Integer getCriticId() {
		return criticId;
	}
	public void setCriticId(Integer criticId) {
		this.criticId = criticId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCirticName() {
		return cirticName;
	}
	public void setCirticName(String cirticName) {
		this.cirticName = cirticName;
	}
	public Integer getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}






}
