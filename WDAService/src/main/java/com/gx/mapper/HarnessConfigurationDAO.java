package com.gx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gx.po.HarnessConfiguration;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.NodeVo;

/**
 * HarnessConfigurationDAO继承基类
 */
@Repository
public interface HarnessConfigurationDAO extends MyBatisBaseDao<HarnessConfiguration, Integer> {

	/**
	 * 查询线束段配置信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<NodeVo> selectWiringHarnessInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 查询线束段配置信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @return
	 */
	public int selectWiringHarnessInfoRows(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId);

	/**
	 * 查询线束段信息是否重复，新增
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationLevelId
	 *            配置id
	 * @param canConfigurationId
	 *            通道id
	 * @param harness
	 *            线束段
	 * @param harnessConfigurationId
	 *            （线束段id）主键id
	 * @return
	 */
	public int selectWiringHarnessGroup(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId,
			@Param("canConfigurationId") Integer canConfigurationId, @Param("harness") String harness,
			@Param("harnessConfigurationId") Integer harnessConfigurationId);

	/**
	 * 绑定线束段下拉框
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationId
	 *            配置id
	 * @return
	 */
	public List<AppendOptionVo> selectHarnessBoxInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationId") Integer configurationId);

	/**
	 * 绑定CAN通道下拉框
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationId
	 *            配置id
	 * @return
	 */
	public List<AppendOptionVo> selectCanPassageBoxInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationId") Integer configurationId);

	/**
	 * 通过ID查询线束段的主键ID
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationLevelId
	 *            配置id
	 * @param canConfigurationId
	 *            CAN通道id
	 * @param harness
	 *            线束段
	 * @return
	 */
	public HarnessConfiguration selectPrimaryKeyById(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId,
			@Param("canConfigurationId") Integer canConfigurationId, @Param("harness") String harness);
}