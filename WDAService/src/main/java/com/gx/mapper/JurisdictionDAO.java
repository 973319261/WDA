package com.gx.mapper;

import com.gx.po.Jurisdiction;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * JurisdictionDAO继承基类
 */
@Repository
public interface JurisdictionDAO extends MyBatisBaseDao<Jurisdiction, Integer> {
	/**
	 * 通过userId查询用户权限模块
	 * @param userId
	 * @return
	 */
	public List<Jurisdiction> selectJurisdictionByUserId(Integer userId);
	/**
	 * 批量新增权限
	 * @param roleId 用户类型ID
	 * @param jurisList 权限列表集合
	 * @return
	 */
	public int insertJurisDictionByBatch(@Param("roleId") int roleId,@Param("jurisList")List<Jurisdiction> jurisList);
	
	/**
	 * 通过用户类型查找权限
	 * @param roleId 用户类型ID
	 * @return
	 */
	//public List<JurisDictionVo> selectJurisDictionById(@Param("roleId") int roleId);
	
	/**
	 * 批量修改权限
	 * @param jurisList 权限列表集合
	 * @return
	 */
	public int updateJurisDictionByBatch(@Param("jurisList")List<Jurisdiction> jurisList);
	
	/**
	 * 查询模块信息
	 * @return
	 */
	public List<Jurisdiction> selectJurisdictionInfo();
}