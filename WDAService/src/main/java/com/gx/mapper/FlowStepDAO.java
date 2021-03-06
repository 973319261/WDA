package com.gx.mapper;

import com.gx.po.Flow;
import com.gx.po.FlowStep;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * FlowStepDAO继承基类
 */
@Repository
public interface FlowStepDAO extends MyBatisBaseDao<FlowStep, Integer> {
	/**
	 * 查询小流程步骤信息
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<FlowStep> selectSmallFlowStepInfo(@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 查询小流程步骤信息(数据总条数)
	 * 
	 * @return
	 */
	public int selectSmallFlowStepInfoRows();
	
	/**
	 * 查询信息是否存在
	 * @param flow
	 * @return
	 */
	public int selectDataWhetherExist(FlowStep flowStep);
	
	/**
	 * 批量删除小流程信息
	 * @param flowStepids 
	 * @return
	 */
	public int deleteSmallFlowStepInfo(List<Integer> flowStepids);
}