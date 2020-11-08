package com.gx.service;

import java.util.List;

import com.gx.po.ConfigurationLevel;
import com.gx.po.Module;
import com.gx.po.ReVehicleSupplier;
import com.gx.po.Supplier;
import com.gx.po.Vehicle;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.SupplierVo;

public interface ISupplierService {

	/**
	 * 查询模块供应信息
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
	public List<SupplierVo> selectSupplierInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, Integer startIndex, Integer pageSize);

	/**
	 * 查询模块供应信息(数据总条数)
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
	public int selectSupplierInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId);

	/**
	 * 通过配置等级ID查询模块下拉框
	 * 
	 * @param configurationLevelId
	 *            配置等级ID
	 * @return
	 */
	public List<AppendOptionVo> selectModuleByConfigurationLevelId(Integer configurationLevelId, Integer vehicleId);

	/**
	 * 通过模块ID查询供应商下拉框
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return
	 */
	public List<AppendOptionVo> selectSupplierInfoByModuleId(Integer moduleId, Integer configurationLevelId,
			Integer vehicleId);

	/**
	 * 新增供应商模块信息
	 * 
	 * @param reVehicleSupplier
	 * @return
	 */
	public JsonReturn insertSupplierInfo(ReVehicleSupplier reVehicleSupplier);

	/**
	 * 修改供应商模块信息
	 * 
	 * @param reVehicleSupplier
	 * @return
	 */
	public JsonReturn updateSupplierInfo(ReVehicleSupplier reVehicleSupplier);

	/**
	 * 通过车型ID查询车型图片
	 * 
	 * @param carTypeId
	 *            车型ID
	 * @return
	 */
	public Vehicle selectCarTypeImageById(Integer vehicleId);

	/**
	 * 保存车型图片
	 * 
	 * @param carType
	 * @return
	 */
	public JsonReturn saveCarTypeImage(Vehicle vehicle, String savePath);

	/**
	 * 查询车型信息
	 * 
	 * @param vehicleName
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Vehicle> selectVehicleInfo(String vehicleName, int startIndex, int pageSize);

	/**
	 * 查询车型信息(数据总条数)
	 * 
	 * @param vehicleName
	 *            车型名称
	 * @return
	 */
	public int selectVehicleInfoRows(String vehicleName);

	/**
	 * 新增车型信息
	 * 
	 * @param vehicle
	 * @return
	 */
	public JsonReturn insertVehicleInfo(Vehicle vehicle);

	/**
	 * 修改车型信息
	 * 
	 * @param vehicle
	 * @return
	 */
	public JsonReturn updateVehicleInfo(Vehicle vehicle, String savePath);

	/**
	 * 删除车型信息
	 * 
	 * @param vehicleId
	 * @return
	 */
	public JsonReturn deleteVehicleInfo(int vehicleId, String savePath);

	/**
	 * 查询模块信息
	 * 
	 * @param moduleName
	 *            模块名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Module> selectModuleMessage(String moduleName, int startIndex, int pageSize);

	/**
	 * 查询模块信息(数据总条数)
	 * 
	 * @param moduleName
	 *            模块名称
	 * @return
	 */
	public int selectModuleMessageRows(String moduleName);

	/**
	 * 新增模块信息
	 * 
	 * @param module
	 *            模块实体类
	 * @return
	 */
	public JsonReturn insertModuleInfo(Module module);

	/**
	 * 修改模块信息
	 * 
	 * @param module
	 *            模块实体类
	 * @return
	 */
	public JsonReturn updateModuleInfo(Module module);

	/**
	 * 删除模块信息
	 * 
	 * @param moduleId
	 *            模块id
	 * @return
	 */
	public JsonReturn deleteModuleInfo(int moduleId);

	/**
	 * 查询供应商信息
	 * 
	 * @param supplierName
	 *            供应商名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Supplier> selectSupplierInfo(String supplierName, int startIndex, int pageSize);

	/**
	 * 查询供应商信息(数据总条数)
	 * 
	 * @param supplierName
	 *            供应商名称
	 * @return
	 */
	public int selectSupplierInfoRows(String supplierName);

	/**
	 * 新增供应商信息
	 * 
	 * @param supplier
	 *            供应商实体类
	 * @return
	 */
	public JsonReturn insertSupplierMessage(Supplier supplier);

	/**
	 * 修改供应商信息
	 * 
	 * @param supplier
	 *            供应商实体类
	 * @return
	 */
	public JsonReturn updateSupplierMessage(Supplier supplier);

	/**
	 * 删除供应商信息
	 * 
	 * @param supplierId
	 *            供应商id
	 * @return
	 */
	public JsonReturn deleteSupplierMessage(int supplierId);

	/**
	 * 通过车型ID查询配置等级信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	public List<AppendOptionVo> selectConfigurationLevelInfoById(Integer vehicleId);

	/**
	 * 删除供应商联合信息
	 * 
	 * @param relevanceId
	 *            关联id
	 * @return
	 */
	public JsonReturn deleteSupplierInfo(int relevanceId);

	/**
	 * 查询配置信息
	 * 
	 * @param configurationLevelName
	 *            配置名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ConfigurationLevel> selectConfigurationInfo(String configurationLevelName, int startIndex,
			int pageSize);

	/**
	 * 查询配置信息(数据总条数)
	 * 
	 * @param configurationLevelName
	 *            配置名称
	 * @return
	 */
	public int selectConfigurationInfoRows(String configurationLevelName);

	/**
	 * 新增配置信息
	 * 
	 * @param configurationLevel
	 *            配置实体类
	 * @return
	 */
	public JsonReturn insertConfigurationInfo(ConfigurationLevel configurationLevel);

	/**
	 * 修改配置信息
	 * 
	 * @param configurationLevel
	 *            配置实体类
	 * @return
	 */
	public JsonReturn updateConfigurationInfo(ConfigurationLevel configurationLevel);

	/**
	 * 删除配置信息
	 * 
	 * @param configurationLevelId
	 *            配置id
	 * @return
	 */
	public JsonReturn deleteConfigurationInfo(int configurationLevelId);

}
