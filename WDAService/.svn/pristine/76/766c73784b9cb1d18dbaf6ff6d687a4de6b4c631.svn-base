package com.gx.mapper;

import com.gx.po.ReVehicleSupplier;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.SupplierVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ReVehicleSupplierDAO继承基类
 */
@Repository
public interface ReVehicleSupplierDAO extends MyBatisBaseDao<ReVehicleSupplier, Integer> {
	/**
	 * 查询关联车型的数据(总条数)
	 * 
	 * @param vehicleId
	 *            车型id
	 * @return
	 */
	public int selectConnectVehicleRows(@Param("vehicleId") Integer vehicleId);

	/**
	 * 查询关联配置的数据(总条数)
	 * 
	 * @param configurationLevelId
	 *            配置id
	 * @return
	 */
	public int selectConnectConfigurationRows(@Param("configurationLevelId") Integer configurationLevelId);

	/**
	 * 查询关联模块的数据(总条数)
	 * 
	 * @param moduleId
	 *            模块id
	 * @return
	 */
	public int selectConnectModuleRows(@Param("moduleId") Integer moduleId);

	/**
	 * 查询关联供应商的数据(总条数)
	 * 
	 * @param supplierId
	 *            供应商id
	 * @return
	 */
	public int selectConnectSupplierRows(@Param("supplierId") Integer supplierId);

	/**
	 * 查询模块供应商信息
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
	public List<SupplierVo> selectSupplierInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 查询模块供应商(数据总条数)
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
	public int selectSupplierInfoRows(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId);

	/**
	 * 通过车型ID查询配置等级信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	public List<AppendOptionVo> selectConfigurationLevelInfoById(@Param("vehicleId") Integer vehicleId);

	/**
	 * 通过配置等级ID查询模块下拉框
	 * 
	 * @param configurationLevelId
	 *            配置等级ID
	 * @return
	 */
	public List<AppendOptionVo> selectModuleByConfigurationLevelId(
			@Param("configurationLevelId") Integer configurationLevelId, @Param("vehicleId") Integer vehicleId);

	/**
	 * 通过模块ID查询供应商下拉框
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return
	 */
	public List<AppendOptionVo> selectSupplierInfoByModuleId(@Param("moduleId") Integer moduleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("vehicleId") Integer vehicleId);

	/**
	 * 查询供应商组合信息，用于新增供应商校验级联关系
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationLevelId
	 *            配置id
	 * @param moduleId
	 *            模块id
	 * @param supplierId
	 *            供应商id
	 * @param relevanceId
	 *            关联id
	 * @return
	 */
	public int selectSupplierGroup(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("relevanceId") Integer relevanceId);

	/**
	 * 通过ID查询关联的主键ID
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationLevelId
	 *            配置id
	 * @param moduleId
	 *            模块id
	 * @param supplierId
	 *            供应商id
	 * @return
	 */
	public ReVehicleSupplier selectPrimaryKeyById(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId);

	/**
	 * 通过车型ID查询模块信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	public List<AppendOptionVo> selectModuleByVehicleId(@Param("vehicleId") Integer vehicleId);

	/**
	 * 查询供应商信息(小流程)
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return
	 */
	public List<AppendOptionVo> selectSupplierById(@Param("moduleId") Integer moduleId,
			@Param("vehicleId") Integer vehicleId);
}