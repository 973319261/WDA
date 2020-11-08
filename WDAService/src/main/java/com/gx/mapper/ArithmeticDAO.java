package com.gx.mapper;

import com.gx.po.Arithmetic;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.ArithmeticVo;
import com.gx.vo.DllVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ArithmeticDAO继承基类
 */
@Repository
public interface ArithmeticDAO extends MyBatisBaseDao<Arithmetic, Integer> {
	/**
	 * 修改SeedToKey访问量访问量
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticLevelId
	 *            安全算法等级ID
	 * @return
	 */
	public boolean updateSeedToKeyVisitNum(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("arithmeticLevelId") Integer arithmeticLevelId);

	/**
	 * 修改VinToPin算法访问量
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	public boolean updateVinToPinVisitNum(Integer vehicleId);

	/**
	 * 修改VinToEsk算法访问量
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	public boolean updateVinToEskVisitNum(Integer vehicleId);

	/**
	 * 修改Seed&PinToKey访问量
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID c
	 * @return
	 */
	public boolean updateSeedAndPinToKeyVisitNum(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId);

	/**
	 * 查询VinToPinDll文件信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticLevelId
	 *            安全算法等级ID
	 * @return
	 */
	public DllVo selectSeedToKeyDll(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("arithmeticLevelId") Integer arithmeticLevelId);

	/**
	 * 查询VinToPinDll文件信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	public DllVo selectVinToPinDll(Integer vehicleId);

	/**
	 * 查询VinToEskDll文件信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	public DllVo selectVinToEskDll(Integer vehicleId);

	/**
	 * 查询SeedAndPinToKeyDll文件信息
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
	public DllVo selectSeedAndPinToKeyDll(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId);

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
	public List<ArithmeticVo> selectSeedToKeyInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("arithmeticName") String arithmeticName,
			@Param("strName") String strName,@Param("sortType") String sortType,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

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
	public int selectSeedToKeyInfoRows(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("arithmeticName") String arithmeticName);

	/**
	 * 查询安全算法(绑定下拉框)
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectArithmeticInfo(@Param("algorithmType") Integer algorithmType);

	/**
	 * 查询Seed转Key管理信息是否存在
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticLevelid
	 *            算法等级ID
	 * @return
	 */
	public int selectSeedToKeyWhetherExist(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("arithmeticLevelid") Integer arithmeticLevelid);

	/**
	 * 通过算法ID删除供应商算法信息
	 * 
	 * @param arithmeticId
	 *            算法ID
	 * @return
	 */
	public int deleteArithmeticSupplierInfo(@Param("arithmeticId") Integer arithmeticId);

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
	public List<ArithmeticVo> selectArithmeticVehicle(@Param("vehicleId") Integer vehicleId,
			@Param("arithmeticName") String arithmeticName, @Param("algorithmType") Integer algorithmType,
			@Param("strName") String strName, @Param("sortType") String sortType,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

	/**
	 * 查询Vin转Pin管理信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param arithmeticName
	 *            安全算法
	 * @return
	 */
	public int selectArithmeticVehicleRows(@Param("vehicleId") Integer vehicleId,
			@Param("arithmeticName") String arithmeticName, @Param("algorithmType") Integer algorithmType);

	/**
	 * 删除Vin转Pin管理信息
	 * 
	 * @param arithmeticId
	 *            算法ID
	 * @return
	 */
	public int deleteVinTurnPinInfo(@Param("arithmeticId") Integer arithmeticId);

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
	public List<ArithmeticVo> selectSeedAndPinToKeyInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("arithmeticName") String arithmeticName,
			@Param("strName") String strName, @Param("sortType") String sortType,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	
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
	public int selectSeedAndPinToKeyInfoRows(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("arithmeticName") String arithmeticName);
	
	/**
	 * 查询SeedAndPinToKey算法信息是否存在
	 *@param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticDelta
	 * 			  算法变量
	 * @return
	 */
	public int selectSeedAndPinToKeyWhetherExist(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("arithmeticDelta") String arithmeticDelta);
}