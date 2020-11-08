package com.gx.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gx.vo.AppendOptionVo;
import com.gx.vo.ArithmeticVo;
import com.gx.vo.JsonReturn;

public interface ISecurityService {
	/**
	 * 查询Seed转Key管理信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticName
	 *            安全算法名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ArithmeticVo> selectSeedToKeyInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String arithmeticName, String strName, String sortType, Integer startIndex,
			Integer pageSize);

	/**
	 * 查询Seed转Key管理信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticName
	 *            安全算法名称
	 * @return
	 */
	public int selectSeedToKeyInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String arithmeticName);

	/**
	 * 查询安全算法(绑定下拉框)
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectArithmeticInfo(Integer algorithmType);

	/**
	 * 查询算法级别(绑定下拉框)
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectArithmeticLevelInfo();

	/**
	 * 新增Seed转Key信息
	 * 
	 * @param arithmetic
	 * @return
	 */
	public JsonReturn insertSeedToKeyInfo(ArithmeticVo arithmetic, String path);

	/**
	 * 删除Seed转Key信息
	 * 
	 * @param arithmeticId
	 *            算法ID
	 * @param arithmeticLevelId
	 *            算法等级ID
	 * @param path
	 *            文件路径
	 * @return
	 */
	public JsonReturn deleteSeedToKeyInfo(Integer arithmeticId, Integer arithmeticLevelId, String path);

	/**
	 * 查询Vin转Pin管理信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param arithmeticName
	 *            安全算法
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ArithmeticVo> selectVinToPinInfo(Integer vehicleId, String arithmeticName, String strName,
			String sortType, Integer startIndex, Integer pageSize);

	/**
	 * 查询Vin转Pin管理信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param arithmeticName
	 *            安全算法
	 * @return
	 */
	public int selectVinToPinInfoRows(Integer vehicleId, String arithmeticName);

	/**
	 * 新增车型算法信息
	 * 
	 * @param arithmetic
	 * @param path
	 *            路径
	 * @param algorithmType
	 *            算法类型
	 * @return
	 */
	public JsonReturn insertArithmeticVehicleInfo(ArithmeticVo arithmetic, String path, Integer algorithmType);

	/**
	 * 删除车型算法信息
	 * 
	 * @param arithmeticId
	 *            算法ID
	 * @param path
	 *            路径
	 * @param algorithmType
	 *            算法类型
	 * @return
	 */
	public JsonReturn deleteArithmeticVehicleInfo(Integer arithmeticId, String path, Integer algorithmType);

	/**
	 * 查询Vin转Pin管理信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param arithmeticName
	 *            算法名称
	 * @param strName
	 * @param sortType
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ArithmeticVo> selectVinToEskInfo(Integer vehicleId, String arithmeticName, String strName,
			String sortType, Integer startIndex, Integer pageSize);

	/**
	 * 查询Vin转Pin管理信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param arithmeticName
	 *            算法名称
	 * @return
	 */
	public int selectVinToEskInfoRows(Integer vehicleId, String arithmeticName);

	/**
	 * 查询SeedAndPinToKey信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticName
	 *            安全算法
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            倒叙、升序
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ArithmeticVo> selectSeedAndPinToKeyInfo(Integer vehicleId, Integer configurationLevelId,
			Integer moduleId, Integer supplierId, String arithmeticName, String strName, String sortType,
			Integer startIndex, Integer pageSize);

	/**
	 * 查询SeedAndPinToKey信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticName
	 *            安全算法
	 * @return
	 */
	public int selectSeedAndPinToKeyInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String arithmeticName);

	/**
	 * 新增SeedAndPinToKey信息
	 * 
	 * @param arithmetic
	 * @param path
	 * @return
	 */
	public JsonReturn insertSeedAndPinToKeyInfo(ArithmeticVo arithmetic, String path);

	/**
	 * 删除SeedAndPinToKey信息
	 * 
	 * @param arithmeticId
	 *            算法ID
	 * @param path
	 * @return
	 */
	public JsonReturn deleteSeedAndPinTurnKeyInfo(Integer arithmeticId, String path);
}
