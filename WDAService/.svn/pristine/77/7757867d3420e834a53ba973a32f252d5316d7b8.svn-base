package com.gx.mapper;

import com.gx.po.Flow;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.FlowVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * FlowDAO继承基类
 */
@Repository
public interface FlowDAO extends MyBatisBaseDao<Flow, Integer> {
	/**
	 * 查询小流程信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param flowId
	 *            流程ID
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<FlowVo> selectFlowInfo(@Param("vehicleId") Integer vehicleId, @Param("flowId") Integer flowId,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

	/**
	 * 查询小流程信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param flowId
	 *            流程ID
	 * @return
	 */
	public int selectFlowInfoRows(@Param("vehicleId")Integer vehicleId,@Param("flowId") Integer flowId);

	/**
	 * 查询小流程信息（绑定下拉框）
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectFlowInfoToDownBox();
}