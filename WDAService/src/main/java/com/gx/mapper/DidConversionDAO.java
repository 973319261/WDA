package com.gx.mapper;

import com.gx.po.DidConversion;
import com.gx.vo.AppendOptionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * DidConversionDAO继承基类
 */
@Repository
public interface DidConversionDAO extends MyBatisBaseDao<DidConversion, Integer> {
	/**
	 * 查询DID库
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<DidConversion> findDIDContent(@Param("didName")String didName, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);
	
	
	
	/**
	 * 查询DID库总数
	 * @return
	 */
	public Integer findDIDContentCount(@Param("didName")String didName);
	
	
	/**
	 * 查询did是否存在
	 * @param did
	 * @return
	 */
	public DidConversion findDidByNameType(DidConversion did);
	
	/**
	 * 单条新增到DID仓库
	 * @param did
	 * @return
	 */
	public Integer insertDIDContent(DidConversion did);
	
	/**
	 * 修改DID信息
	 * @param did
	 * @return
	 */
	public Integer updateDIDContent(DidConversion did);
	
	
	/**
	 * 批量删除DID信息
	 * @param ids
	 * @return
	 */
	public Integer delDIDContent(List<Integer> ids);
	
	
	/**
	 * 根据模块，供应商查询DID
	 * @param moudleId
	 * @param supplierId
	 * @return
	 */
	public List<AppendOptionVo> findDidByModSup(@Param("moudleId")Integer moudleId,@Param("supplierId")Integer supplierId);
	
}