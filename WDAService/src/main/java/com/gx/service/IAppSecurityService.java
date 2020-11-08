package com.gx.service;

import javax.servlet.http.HttpServletRequest;

import com.gx.vo.JsonReturn;
public interface IAppSecurityService {
	/**
	 * 通过Dll计算（算法）
	 * @param inputValue 输入值
	 * @param pin seed&pin-->key中pin值(不是必须)
	 * @param vehicleId 车型ID
	 * @param configurationLevelId 配置等级（不是必须）
	 * @param moduleId 模块ID（不是必须）
	 * @param supplierId 供应商ID（不是必须）
	 * @param arithmeticLevelId 安全算法等级ID（不是必须）
	 * @param userId 用户ID
	 * @param typeId 算法类型ID（seed--key ...）
	 * @param request
	 * @return
	 */
	public JsonReturn calculateByDll(String inputValue, String pin, Integer vehicleId, Integer configurationLevelId,
			Integer moduleId,Integer supplierId,Integer arithmeticLevelId,
			Integer userId, Integer typeId, HttpServletRequest request);
	/**
	 * 查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的车型
	 * @param algorithmType 算法类型 1或者4
	 * @return
	 */
	public JsonReturn selectVehicleRelateArithmetic(Integer roleId,Integer algorithmType,String moduleName);
	/**
	 * 查询算法与VINToPin（2）或者VINToEsk（3）关联的车型
	 * @param algorithmType 算法类型2或者3
	 * @return
	 */
	public JsonReturn selectVehicleRelateArithmeticVehicle(Integer algorithmType);
	/**
	 * 通过车型ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的配置
	 * @param vehicleId 车型ID
	 * @param algorithmType 算法类型（ 1或者4）
	 * @return
	 */
	public JsonReturn selectConfigurationRelateArithmetic(Integer vehicleId,Integer algorithmType);
	/**
	 * 通过车型ID和配置等级ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的模块
	 * @param vehicleId 车型ID
	 * @param configurationLevelId 配置等级ID
	 * @param algorithmType 算法类型（ 1或者4）
	 * @return
	 */
	public JsonReturn selectModuleRelateArithmetic(Integer vehicleId,
			Integer configurationLevelId,Integer roleId,Integer algorithmType);
	/**
	 *   通过车型ID、配置等级ID、模块ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的供应商
	 * @param vehicleId 车型ID
	 * @param configurationLevelId 配置等级ID
	 * @param moudleId 模块ID
	 * @param algorithmType 算法类型（ 1或者4）
	 * @return
	 */
	public JsonReturn selectSupplierRelateArithmetic(Integer vehicleId,
			Integer configurationLevelId,Integer moudleId,
			Integer algorithmType);
	/**
	 * 查询算法与SeedToKey关联的算法等级
	 * @param vehicleId 车型ID
	 * @param configurationLevelId 配置等级ID
	 * @param moudleId 模块ID
	 * @param supplierId 供应商ID
	 * @return
	 */
	public JsonReturn selectLevelRelateSeedToKey(Integer vehicleId,
			Integer configurationLevelId,Integer moudleId,
			Integer supplierId);

	
}
