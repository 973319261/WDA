package com.gx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gx.po.Node;
import com.gx.vo.NodeVo;

/**
 * NodeDAO继承基类
 */
@Repository
public interface NodeDAO extends MyBatisBaseDao<Node, Integer> {
	/**
	 * 查询节点信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<NodeVo> selectNodeInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 查询节点信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @return
	 */
	public int selectNodeInfoRows(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId);

	/**
	 * 查询节点信息是否存在，用于新增
	 * 
	 * @param relevanceId
	 *            车型供应商主键id
	 * @param harnessConfigurationId
	 *            线束段配置主键id
	 * @param txId
	 *            发送id
	 * @param rxId
	 *            接收id
	 * @param nodeId
	 *            节点主键id（用于修改，排除自身）
	 * @return
	 */
	public int selectNodeWhetherExist(@Param("relevanceId") Integer relevanceId,
			@Param("harnessConfigurationId") Integer harnessConfigurationId, @Param("txId") String txId,
			@Param("rxId") String rxId, @Param("nodeId") Integer nodeId);

	/**
	 * 通过id查询节点信息
	 * 
	 * @param canConfigurationId
	 *            CAN通道id
	 * @param moduleId
	 *            模块id
	 * @param configurationLevelId
	 *            配置id
	 * @return
	 */
	public List<NodeVo> selectNodeInfoById(@Param("canConfigurationId") Integer canConfigurationId,
			@Param("moduleId") Integer moduleId, @Param("configurationLevelId") Integer configurationLevelId);

	/**
	 * 查询节点详情信息
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<NodeVo> selectNodeDetailInfo(@Param("vehicleId") Integer vehicleId,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

	/**
	 * 查询节点详情信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型id
	 * @return
	 */
	public int selectNodeDetailInfoRows(@Param("vehicleId") Integer vehicleId);

	/**
	 * 批量删除节点数据
	 * 
	 * @param listId
	 *            主键id
	 * @return
	 */
	public int deleteNodeDetailInfo(List<Integer> listId);

}