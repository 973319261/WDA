package com.gx.mapper;

import com.gx.po.DidType;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * DidTypeDAO继承基类
 */
@Repository
public interface DidTypeDAO extends MyBatisBaseDao<DidType, Integer> {
	/**
	 * 通过didTypeId删除DID、DID配置和状态描述信息(快照)
	 * @param didTypeId
	 * @return
	 */
	public int deleteSnapshotInfo(@Param("didTypeId") Integer didTypeId);
}