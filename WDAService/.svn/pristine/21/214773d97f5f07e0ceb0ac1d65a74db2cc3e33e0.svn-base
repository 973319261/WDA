package com.gx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gx.po.Supplier;
import com.gx.vo.AppendOptionVo;

/**
 * SupplierDAO继承基类
 */
@Repository
public interface SupplierDAO extends MyBatisBaseDao<Supplier, Integer> {

	/**
	 * 通过车型ID、配置等级ID、模块ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的供应商
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moudleId
	 *            模块ID
	 * @param algorithmType
	 *            算法类型（ 1或者4） 查询算法与SeedToKey关联的供应商
	 * @param moudleId
	 *            查询供应商信息by 名称、编号
	 * @param sup
	 * @return
	 */
	public List<AppendOptionVo> selectSupInfo(Supplier sup);

	/**
	 * 查询算法与SeedToKey关联的供应商
	 * 
	 * @param moudleId
	 * @return
	 */
	public List<AppendOptionVo> selectSupplierRelateArithmetic(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moudleId") Integer moudleId,
			@Param("algorithmType") Integer algorithmType);

	/**
	 * 查询供应商信息
	 * 
	 * @param supplierName
	 *            供应商名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Supplier> selectSupplierInfo(@Param("supplierName") String supplierName,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 查询供应商信息(数据总条数)
	 * 
	 * @param supplierName
	 *            供应商名称
	 * @return
	 */
	public int selectSupplierInfoRows(@Param("supplierName") String supplierName);

	/**
	 * 查询供应商信息是否已存在
	 * 
	 * @param supplierName
	 *            供应商名称
	 * @param supplierId
	 *            供应商id
	 * @return
	 */
	public Supplier selectSupplierWhetherExist(@Param("supplierName") String supplierName,
			@Param("supplierId") int supplierId);
	
	/**
	 * 查询供应商代码是否存在
	 * @param supplierCode 供应商代码
	 * @param supplierId 供应商ID
	 * @return
	 */
	public int selectSupplierCodeWhetherExist(@Param("supplierCode") String supplierCode,
			@Param("supplierId") int supplierId);

	
	
	
	
	/**
	 * 根据did转换插叙模块
	 * @param moduleId
	 * @return
	 */
	public List<AppendOptionVo> selectSupplierByModuleId(Integer moduleId);
}