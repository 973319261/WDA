package com.gx.mapper;

import com.gx.po.NoticeInform;
import com.gx.vo.InformVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * NoticeInformDAO继承基类
 */
@Repository
public interface NoticeInformDAO extends MyBatisBaseDao<NoticeInform, Integer> {
	/**
	 * 批量新增
	 * @param inform
	 * @return
	 */
	public int insertNoticeInformByBatch(List<NoticeInform> informList);
	/**
	 * 查询是否有未读文件通知消息
	 * @param userId
	 * @return
	 */
	public int selectIsReadState(Integer userId);
	/**
	 *  通过用户ID查询文件通知
	 * @param userId
	 * @return
	 */
	public List<InformVo> selectNoticeInformByUserId(int userId);
	/**
	 * 修改公告为已读状态
	 * @param userId
	 * @param informId
	 * @return
	 */
	public boolean updateInformState(@Param("userId") Integer userId,@Param("noticeId")Integer noticeId);
}