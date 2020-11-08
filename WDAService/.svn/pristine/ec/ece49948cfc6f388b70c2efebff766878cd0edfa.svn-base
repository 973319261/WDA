package com.gx.service;

import java.util.List;

import com.gx.po.Comment;
import com.gx.po.DidConversion;
import com.gx.po.Eol;
import com.gx.po.Inform;
import com.gx.po.Notice;
import com.gx.po.ReVehicleSupplier;
import com.gx.vo.CommentsVo;
import com.gx.vo.DidConversionVo;
import com.gx.vo.FilesVo;
import com.gx.vo.InformVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.NoticeVo;

public interface IShareService {

	/***************************** ECU产线EOL操作 start ******************************/

	/**
	* 查询ECU产线EOL操作
	* 
	* @param inputValue 输入值
	* @param startIndex
	* @param pageSize
	* @return
	*/
	public List<Eol> selectEolOperationInfo(String inputValue, int startIndex, int pageSize);
	
	/**
	 * 查询ECU产线EOL操作(数据总条数)
	 * 
	 * @param inputValue
	 *            输入值
	 * @return
	 */
	public int selectEolOperationInfoRows(String inputValue);
	
	/**
	* 新增、修改ECU产线EOL操作
	* @param eol
	* @return
	*/
	public JsonReturn saveEcuProductionLineInfo(Eol eol);
	
	/***************************** ECU产线EOL操作 over ******************************/
	
	/**
	* 删除ECU管理信息
	* 
	* @param ecuId
	* @return
	*/
	public JsonReturn deleteEcuInfo(int ecuId, int item);
	
	/***************************** 通知管理 start ******************************/
	
	/**
	* 查询通知管理信息
	* @param informTitle 通知标题
	* @param startIndex
	* @param pageSize
	* @return
	*/
	public List<InformVo> selectInform(String informTitle, int startIndex, int pageSize);
	
	/**
	* 查询通知管理信息条数
	* 
	* @param informTitle 通知标题
	* @return
	*/
	public int selectInformRows(String informTitle);
	
	/***************************** 通知管理 start ******************************/
	
	/**
	* 新增通知管理信息
	* @param inform
	* @return
	*/
	public JsonReturn insertInform(Inform inform);
	
	/**
	 * 删除通知管理信息
	 * 
	 * @param informId
	 *            通知管理ID
	 * @return
	 */
	
	public JsonReturn deleteInform(int informId);
	  
	/***************************** 通知管理 over ******************************/
	
	
	/***************************** 公告 start ******************************/

	/**
	 * 查询公告信息
	 * 
	 * @param noticeTheme
	 *            公告主题
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<NoticeVo> selectNoticeInfo(String noticeTheme, int startIndex, int pageSize);

	/**
	 * 查询公告(数据总条数)
	 * 
	 * @param noticeTheme
	 * @return
	 */
	public int selectNoticeInfoRows(String noticeTheme);

	/**
	 * 通过公告ID查询文件
	 * 
	 * @param noticeId
	 * @return
	 */
	public List<FilesVo> selectFileByNoticeId(int noticeId);

	/**
	 * 新增公告信息
	 * 
	 * @param notice
	 * @return
	 */
	public JsonReturn insertNoticeInfo(Notice notice, FilesVo files);

	/**
	 * 通过公告ID查询公告信息
	 * 
	 * @param noticeId
	 * @return
	 */
	public NoticeVo selectNoticeInfoById(Integer noticeId);

	/**
	 * 修改公告信息
	 * 
	 * @param notice
	 * @param files
	 * @param id
	 * @return
	 */
	public JsonReturn updateNoticeInfo(Notice notice, FilesVo files, Integer id);

	/**
	 * 通过公告ID查询评论信息
	 * @param noticeId 公告ID
	 * @param userName 用户名
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<CommentsVo> selectCommentInfo(Integer noticeId, String userName, int startIndex, int pageSize);

	/**
	 * 通过公告ID查询评论信息(数据总条数)
	 * @param noticeId 公告ID
	 * @param userName 用户名
	 * @return
	 */
	public int selectCommentInfoRows(Integer noticeId, String userName);
	
	/**
	 * 通过评论ID查询回复信息
	 * @param replyId 评论ID
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<CommentsVo> selectCommentReplyInfo(Integer noticeId,Integer replyId,Integer startIndex, Integer pageSize);
	
	/**
	 * 通过评论ID查询回复信息(数据总条数)
	 * @param replyId 评论ID
	 * @return
	 */
	public int selectCommentReplyInfoRows(Integer noticeId,Integer replyId);

	/**
	 * 删除公告信息
	 * @param noticeId 公告ID
	 * @param filePath 文件路径
	 * @return
	 */
	public JsonReturn deleteNoticeInfo(Integer noticeId,String filePath);

	/**
	 * 添加回复
	 * @param comment
	 * @return
	 */
	public JsonReturn insertReplyInfo(Comment comment);
	
	/**
	 * 删除评论信息
	 * @param commentId 评论ID
	 * @return
	 */
	public JsonReturn deleteCommentInfo(Integer commentId);
	
	//***************************** 公告 end ******************************/
	
	
	
	
	//***************************** DID转换 start ******************************/
	
	/**
	 * 查询did仓库
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<DidConversion> findDIDContent(String didName,int startIndex,int pageSize);
	
	/**
	 * 查询DID仓库总数
	 * @return
	 */
	public Integer findDIDContentCount(String didName);
	
	
	/**
	 * 查询数据在did库中是否存在
	 * @param did
	 * @return
	 */
	public JsonReturn findDidByNameType(DidConversion did);
	
	/**
	 * 单条新增到DID仓库
	 * @param did
	 * @return
	 */
	public JsonReturn insertDIDContent(DidConversion did);
	
	/**
	 * 修改DID信息
	 * @param did
	 * @return
	 */
	public JsonReturn updateDIDContent(DidConversion did);
	
	
	/**
	 * 批量删除DID信息
	 * @param ids
	 * @return
	 */
	public JsonReturn delDIDContent(List<Integer> ids);
	
	/**
	 * 查询did转换信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<DidConversionVo> selectDidConversionInfo(Integer vehicleId, Integer configurationLevelId,
			Integer moduleId, Integer supplierId, Integer startIndex, Integer pageSize);

	/**
	 * 查询did转换信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public int selectDidConversionInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId);

	/**
	 * 查询did列表信息
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @param didName
	 *            DID名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<DidConversionVo> selectDidListInfo(Integer relevanceId, String didName, Integer startIndex,
			Integer pageSize);

	/**
	 * 查询did列表信息
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @param didName
	 *            DID名称
	 * @return
	 */
	public int selectDidListInfoRows(Integer relevanceId, String didName);
	
	/**
	 * 新增DID转换信息
	 * @param dids
	 * @param relevanceId 模块供应商关联ID
	 * @return
	 */
	public JsonReturn insertDidConversionInfo(List<DidConversion> listDid,Integer relevanceId);
	
	/**
	 * 删除DID转换信息
	 * @param ids
	 * @return
	 */
	public JsonReturn deleteDidConversionInfo(String ids);
	
	/**
	 * 保存DID转换信息
	 * @param vehicleId 车型ID
	 * @param configurationLevelId 配置等级ID
	 * @param moduleId 模块ID
	 * @param supplierId  供应商ID
	 * @param listSession
	 * @return
	 */
	public JsonReturn saveDidConversionInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId,List<DidConversion> listSession);
	
	/**
	 * 通过模块供应商关联ID删除DID转换信息
	 * @param ids
	 * @return
	 */
	public JsonReturn deleteDidInfoById(String ids);
	
	/**
	 * 修改DID转换的模块供应商信息
	 * @param vehicleSupplier
	 * @param reDidRelevanceId
	 * @return
	 */
	public JsonReturn updateDidSupplierInfo(ReVehicleSupplier vehicleSupplier,Integer relevanceId);

	//***************************** DID转换  end ******************************/
	
}
