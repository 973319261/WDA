package com.gx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gx.po.FaultCode;
import com.gx.vo.FaultCodeResultVo;
import com.gx.vo.FaultCodeVo;

/**
 * FaultCodeDAO继承基类
 */
@Repository
public interface FaultCodeDAO extends MyBatisBaseDao<FaultCode, Integer> {
	/**
	 * 查询故障码信息
	 * 
	 * @param dtc
	 * @param vehicleId
	 * @param configureId
	 * @param moduleId
	 * @return
	 */
	public FaultCodeResultVo selectFaultCode(@Param("dtc") String dtc, @Param("vehicleId") Integer vehicleId,
			@Param("configureId") Integer configureId, @Param("moduleId") Integer moduleId);

	/**
	 * 通过hex查询故障码信息
	 * 
	 * @param dtc
	 * @param vehicleId
	 * @param configureId
	 * @param moduleId
	 * @return
	 */
	public FaultCodeResultVo selectFaultCodeByHexDtc(@Param("hexDtc") String hexDtc,
			@Param("vehicleId") Integer vehicleId, @Param("configureId") Integer configureId,
			@Param("moduleId") Integer moduleId);

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
	public List<FaultCodeVo> selectSupFaultCount(FaultCodeVo fault);

	/**
	 * 查询故障码信息是否存在
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param dtc
	 *            故障码
	 * @return
	 */
	public int selectFaultCodeWhetherExist(@Param("relevanceId") Integer relevanceId, @Param("dtc") String dtc);

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
	public List<FaultCodeVo> selectfaultCodeDetailInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("dtc") String dtc, @Param("strName") String strName,
			@Param("sortType") String sortType, @Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

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
	public int selectfaultCodeDetailInfoRows(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("dtc") String dtc);

	/**
	 * 查询重复故障码的条数
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @param dtc
	 *            故障码
	 * @return
	 */
	public int selectRepetitionFaultCodeRows(@Param("relevanceId") Integer relevanceId, @Param("dtc") String dtc,
			@Param("faultCodeId") Integer faultCodeId);
	
	/**
	 * 通过模块供应商关联ID查询故障码信息
	 * @param relevanceId 模块供应商关联ID
	 * @return
	 */
	public List<FaultCodeVo> selectFaultCodeById(@Param("relevanceId") Integer relevanceId);

}