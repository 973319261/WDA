package com.gx.mapper;

import com.gx.po.DidConversion;
import com.gx.po.ReDidRelevance;
import com.gx.vo.DidConversionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ReDidRelevanceDAO继承基类
 */
@Repository
public interface ReDidRelevanceDAO extends MyBatisBaseDao<ReDidRelevance, Integer> {
	/**
	 * 查询与模块供应关联的数据(数据总条数)
	 * @param relevanceId 关联ID
	 * @return
	 */
	public int selectConnectRelevanceRows(@Param("relevanceId")Integer relevanceId);
	
	/**
	 * 查询did转换信息
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
	public List<DidConversionVo> selectDidConversionInfo(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId, @Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 查询did转换信息(数据总条数)
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
	public int selectDidConversionInfoRows(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("moduleId") Integer moduleId,
			@Param("supplierId") Integer supplierId);

	/**
	 * 查询did列表信息
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @param didName
	 *            DID名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<DidConversionVo> selectDidListInfo(@Param("relevanceId") Integer relevanceId,
			@Param("didName") String didName, @Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 查询did列表信息
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @param didName
	 *            DID名称
	 * @return
	 */
	public int selectDidListInfoRows(@Param("relevanceId") Integer relevanceId, @Param("didName") String didName);
	
	/**
	 * 批量删除DID转换信息
	 * @param listId
	 * @return
	 */
	public int deleteDidConversionInfo(List<Integer> listId);
	
	/**
	 * 批量新增DID转换信息
	 * @param listDid
	 * @param relevanceId 模块供应关联ID
	 * @return
	 */
	public int saveDidConversionInfo(@Param("listDid")List<DidConversion> listDid, @Param("relevanceId")Integer relevanceId);
	
	/**
	 * 通过主键ID进行批量删除DID转换信息
	 * @param listId
	 * @return
	 */
	public int deleteDidInfoById(List<Integer> listId);
	
	/**
	 * 通过关联ID查询DID转换信息
	 * @param relevanceId 关联ID
	 * @return
	 */
	public List<ReDidRelevance> selectDidRelevanceByRelevanceId(@Param("relevanceId")Integer relevanceId);
	
	/**
	 * 批量修改DID转换信息
	 * @param didRelevances
	 * @return
	 */
	public int updatetDidRelevanceInfoByBatch(@Param("didRelevancesList")List<ReDidRelevance> didRelevances);
}