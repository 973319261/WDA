package com.gx.mapper;

import com.gx.po.InformDetail;
import com.gx.vo.InformVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * InformDetailDAO继承基类
 */
@Repository
public interface InformDetailDAO extends MyBatisBaseDao<InformDetail, Integer> {
	/**
	 * 批量新增
	 * 
	 * @param inform
	 * @return
	 */
	public int insertRelevanceInformByBatch(List<InformDetail> informList);

	/**
	 * 查询是否有未读公告通知消息
	 * 
	 * @param userId
	 * @return
	 */
	public int selectIsReadState(Integer userId);

	/**
	 * 通过用户ID查询公告通知
	 * 
	 * @param userId
	 * @return
	 */
	public List<InformVo> selectInformByUserId(int userId);

	/**
	 * 修改通知为已读状态
	 * 
	 * @param userId
	 * @param informId
	 * @return
	 */
	public boolean updateInformState(@Param("userId") Integer userId, @Param("informId") Integer informId);
}