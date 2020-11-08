package com.gx.mapper;

import com.gx.po.Did;
import com.gx.vo.DidVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * DidDAO继承基类
 */
@Repository
public interface DidDAO extends MyBatisBaseDao<Did, Integer> {
	/**
	 * 查询快照信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param moduleId
	 *            模块ID
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<DidVo> selectSnapshotInfo(@Param("vehicleId") Integer vehicleId, @Param("moduleId") Integer moduleId,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

	/**
	 * 查询快照信息(数据总条数)
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param moduleId
	 *            模块ID
	 * @return
	 */
	public int selectSnapshotInfoRows(@Param("vehicleId") Integer vehicleId, @Param("moduleId") Integer moduleId);

	/**
	 * 查询DID名称是否重复
	 * 
	 * @param didName
	 *            DID名称
	 * @param typeId
	 *            类型(DID或快照)
	 * @return
	 */
	public int selectDidNameWhetherExist(@Param("didName") String didName, @Param("typeId") Integer typeId,
			@Param("didId") Integer didId);
	
	/**
	 * 通过DID名称和类型查询DID信息
	 * @param didName DID名称
	 * @param typeId DID类型
	 * @return
	 */
	public Did selectDidInfoByDidName(@Param("didName") String didName, @Param("typeId") Integer typeId);
}