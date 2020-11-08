package com.gx.mapper;

import com.gx.po.Inform;
import com.gx.vo.InformVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * InformDAO继承基类
 */
@Repository
public interface InformDAO extends MyBatisBaseDao<Inform, Integer> {
	/**
	 * 查询通知管理信息
	 * 
	 * @param informTitle 通知标题
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<InformVo> selectInform(@Param("informTitle") String informTitle, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 查询通知管理信息条数
	 * 
	 * @param informTitle 通知标题
	 * @return
	 */
	public int selectInformRows(@Param("informTitle") String informTitle);
	
	/**
	 * 通过通知管理ID删除通知管理信息
	 * @param informId 通知管理ID
	 * @return
	 */
	public int deleteInformById(@Param("informId") int informId);
	
	/**
	 * 查询所有的通知信息
	 * @return
	 */
	public List<InformVo> selectAllInform();
}