package com.gx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gx.po.ConfigurationLevel;
import com.gx.vo.AppendOptionVo;

/**
 * ConfigurationLevelDAO继承基类
 */
@Repository
public interface ConfigurationLevelDAO extends MyBatisBaseDao<ConfigurationLevel, Integer> {
	/**
	 * 通过车型ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的配置
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param algorithmType
	 *            算法类型（ 1或者4）
	 * @return
	 */
	public List<AppendOptionVo> selectConfigurationRelateArithmetic(@Param("vehicleId") Integer vehicleId,
			@Param("algorithmType") Integer algorithmType);

	/**
	 * 通过dtc和车型ID查询配置
	 * 
	 * @param dtcParam
	 * @param vehicleId
	 * @return
	 */
	public List<AppendOptionVo> selectConfigureByDtc(@Param("dtc") String dtc, @Param("vehicleId") Integer vehicleId);

	/**
	 * 通过hexdtc和车型ID查询配置
	 * 
	 * @param dtcParam
	 * @param vehicleId
	 * @return
	 */
	public List<AppendOptionVo> selectConfigureByHexDtc(@Param("hexHtc") String hexHtc,
			@Param("vehicleId") Integer vehicleId);

	/**
	 * 查询配置信息
	 * 
	 * @param configurationLevelName
	 *            配置名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ConfigurationLevel> selectConfigurationInfo(
			@Param("configurationLevelName") String configurationLevelName, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 查询配置信息(数据总条数)
	 * 
	 * @param configurationLevelName
	 *            配置名称
	 * @return
	 */
	public int selectConfigurationInfoRows(@Param("configurationLevelName") String configurationLevelName);

	/**
	 * 查询配置信息是否已存在
	 * 
	 * @param configurationLevelName
	 *            配置名称
	 * @param configurationLevelId
	 *            配置id
	 * @return
	 */
	public ConfigurationLevel selectConfigurationWhetherExist(
			@Param("configurationLevelName") String configurationLevelName,
			@Param("configurationLevelId") int configurationLevelId);

}