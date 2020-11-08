package com.gx.mapper;

import com.gx.po.ReArithmeticSupplier;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ReArithmeticSupplierDAO继承基类
 */
@Repository
public interface ReArithmeticSupplierDAO extends MyBatisBaseDao<ReArithmeticSupplier, Integer> {
	/**
	 * 查询与模块供应商关联的数据(数据总条数)
	 * @param relevanceId 关联ID
	 * @return
	 */
	public int selectConnectRelevanceRows(@Param("relevanceId") Integer relevanceId);
}