package com.gx.mapper;

import com.gx.po.StateDescription;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * StateDescriptionDAO继承基类
 */
@Repository
public interface StateDescriptionDAO extends MyBatisBaseDao<StateDescription, Integer> {
	/**
	 * 批量新增状态描述信息
	 * @param stateInfo 状态描述信息
	 * @return
	 */
	public int insertStateDescriptionByBatch(@Param("stateInfoList")List<StateDescription> stateInfo);
	
	/**
	 * 通过DID类型ID进行删除状态描述信息
	 * @param didTypeId DID类型ID
	 * @return
	 */
	public int deleteStateDescriptionInfo(@Param("didTypeId")Integer didTypeId);
	
	/**
	 * 通过DID类型ID查询状态描述信息
	 * @param didTypeId DID类型ID
	 * @return
	 */
	public List<StateDescription> selectStateDescribeInfoById(@Param("didTypeId") Integer didTypeId);
}