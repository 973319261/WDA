package com.gx.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gx.po.Supplier;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.CommentsVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.Pagination;

public interface IAppShareService {
	
	/**
	 * 根据公告标题模糊查询公告
	 * @param name
	 * @return
	 */
	public Pagination selectListNoticeByName(String name,int pageSize,int currentPage);
	
	
	/**
	 * 根据公告ID查询公告信息
	 * @param id
	 * @return
	 */
	public JsonReturn selectNoticeById(Integer id,Integer userId);
	/**
	 * 根据通知ID查询通知信息
	 * @param id
	 * @return
	 */
	public JsonReturn selectInformById(Integer informId);
	
	
	/**
	 * 查询评论数据 根据公告ID和上级评论ID
	 * @param noticeId
	 * @param replyId
	 * @return
	 */
	public JsonReturn selectComments(CommentsVo vo);
	
	/**
	 * 查询评论列表
	 * @param vo
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Pagination selectListComment(CommentsVo vo);
	
	
	/**
	 * 通过ID查询评论信息
	 * @param commmentsId
	 * @return
	 */
	public JsonReturn selectdbCom(int commentsId);
	
	
	/**
	 * 新增评论内容
	 * @param comment
	 * @return
	 */
	public JsonReturn addComment(CommentsVo comment);
	
	
	/**
	 * 删除评论 根据主键ID
	 * @param commentsId
	 * @return
	 */
	public JsonReturn delComment(int commentsId);
	/**
	 * 通过用户ID查询公告通知
	 * @param userId
	 * @return
	 */
	public JsonReturn selectInformByUserId(int userId);
	
	
	/**
	 *  通过用户ID查询文件通知
	 * @param userId
	 * @return
	 */
	public JsonReturn selectFileInformByUserId(int userId);
	/**
	 * 修改公告通知为已读状态
	 * @param type
	 * @param userId
	 * @param id
	 * @return
	 */
	public JsonReturn updateInformState(int type,int userId,int id);

	/**
	 * 查询公告通知未读消息条数
	 * @param userId
	 * @return
	 */
	public int selectInformIsReadState(Integer userId);
	/**
	 * 查询文件通知未读消息条数
	 * @param userId
	 * @return
	 */
	public int selectFileInformIsReadState(Integer userId);
	/**
	 * 查询所有用户信息
	 * @param isPush 是否推送
	 * @return
	 */
	public JsonReturn selectUserInfoAll(Boolean isPush);
	
	
	/**
	 * 查询与DID转换有关的模块 -
	 * @return
	 */
	public JsonReturn selectModuleAsDid();
	/**
	 * 根据did转换插叙模块
	 * @param moduleId
	 * @return
	 */
	public JsonReturn selectSupplierByModuleId(Integer moduleId);
	
	
	/**
	 * 根据模块，供应商查询DID
	 * @param moudleId
	 * @param supplierId
	 * @return
	 */
	public JsonReturn findDidByModSup(@Param("moudleId")Integer moudleId,@Param("supplierId")Integer supplierId);
}
