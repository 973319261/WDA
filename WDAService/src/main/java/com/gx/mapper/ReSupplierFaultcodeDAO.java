package com.gx.mapper;

import com.gx.po.ReSupplierFaultcode;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ReSupplierFaultcodeDAO继承基类
 */
@Repository
public interface ReSupplierFaultcodeDAO extends MyBatisBaseDao<ReSupplierFaultcode, Integer> {

	/**
	 * 删除故障码以及关联信息
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @return
	 */
	public int deleteFaultCodeInfoTwo(@Param("relevanceId") Integer relevanceId);
}