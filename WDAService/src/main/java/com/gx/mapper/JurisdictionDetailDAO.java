package com.gx.mapper;

import com.gx.po.JurisdictionDetail;
import com.gx.vo.JurisDictionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * JurisdictionDetailDAO继承基类
 */
@Repository
public interface JurisdictionDetailDAO extends MyBatisBaseDao<JurisdictionDetail, Integer> {
	/**
	 * 通过userId查询用户权限模块
	 * @param userId
	 * @return
	 */
	public List<JurisdictionDetail> selectJurisdictionByUserId(Integer userId);
	/**
	 * 批量新增权限
	 * @param roleId 用户类型ID
	 * @param jurisList 权限列表集合
	 * @return
	 */
	public int insertJurisDictionByBatch(@Param("roleId") int roleId,@Param("jurisList")List<JurisdictionDetail> jurisList);
	
	/**
	 * 通过用户类型查找权限
	 * @param roleId 用户类型ID
	 * @return
	 */
	public List<JurisDictionVo> selectJurisDictionById(@Param("roleId") int roleId);
	
	/**
	 * 批量修改权限
	 * @param jurisList 权限列表集合
	 * @return
	 */
	public int updateJurisDictionByBatch(@Param("jurisList")List<JurisdictionDetail> jurisList);
}