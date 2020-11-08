package com.gx.service;

import java.util.List;

import com.gx.po.ReVehicleSupplier;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.FaultCodeVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.SupplierVo;

public interface IFaultCodeService {

	/**
	 * 查询车型信息（绑定下拉框）
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectCarTypeInfo();

	/**
	 * 查询故障码管理信息
	 * 
	 * @param carTypeId
	 *            车型ID
	 * @param moudleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<SupplierVo> selectFaultCodeInfo(int carTypeId, int moudleId, int supplierId, int startIndex,
			int pageSize);

	/**
	 * 查询故障码管理信息(数据总条数)
	 * 
	 * @param carTypeId
	 *            车型ID
	 * @param moudleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public int selectFaultCodeInfoRows(int carTypeId, int moudleId, int supplierId);

	/**
	 * 查询故障码有关的车型模块供应商信息
	 * 
	 * @param rev
	 * @return
	 */
	public List<FaultCodeVo> selectSupFault(FaultCodeVo fault);

	/**
	 * 查询表格数量
	 * 
	 * @param rev
	 * @return
	 */
	public int selectSupFaultCount(FaultCodeVo fault);

	/**
	 * 新增故障码信息
	 * 
	 * @param listFault
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	public JsonReturn insertFaultCodeInfo(List<FaultCodeVo> listFault, ReVehicleSupplier supplier);

	/**
	 * 查询故障码详情信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param dtc
	 *            故障码
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<FaultCodeVo> selectfaultCodeDetailInfo(Integer vehicleId, Integer configurationLevelId,
			Integer moduleId, Integer supplierId, String dtc, String strName, String sortType, Integer startIndex,
			Integer pageSize);

	/**
	 * 查询故障码详情信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param dtc
	 *            故障码
	 * @return
	 */
	public int selectfaultCodeDetailInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String dtc);

	/**
	 * 新增故障码信息
	 * 
	 * @param faultCode
	 * @return
	 */
	public JsonReturn addFaultCodeInfo(FaultCodeVo faultCode);

	/**
	 * 修改故障码信息
	 * 
	 * @param faultCode
	 * @return
	 */
	public JsonReturn updateFaultCodeInfo(FaultCodeVo faultCode);

	/**
	 * 删除故障码信息(通过主键ID进行删除)
	 * 
	 * @param reId
	 *            关联ID
	 * @param faultCodeId
	 * @return
	 */
	public JsonReturn deleteFaultCodeInfo(Integer reId, Integer faultCodeId);

	/**
	 * 删除故障码信息(通过关联ID进行删除)
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @return
	 */
	public JsonReturn deleteFaultCodeInfoTwo(Integer relevanceId);

	/**
	 * 通过模块供应商关联ID查询故障码信息
	 * 
	 * @param relevanceId
	 *            模块供应商关联ID
	 * @return
	 */
	public List<FaultCodeVo> selectFaultCodeById(Integer relevanceId);
}
