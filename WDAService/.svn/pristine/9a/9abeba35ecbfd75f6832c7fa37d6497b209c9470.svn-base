package com.gx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gx.po.ModuleJurisdictionDetail;
import com.gx.vo.ModuleJurisdictionDetailVo;

/**
 * ModuleJurisdictionDetailDAO继承基类
 */
@Repository
public interface ModuleJurisdictionDetailDAO extends MyBatisBaseDao<ModuleJurisdictionDetail, Integer> {

	/**
	 * 通过角色id查询权限模块明细
	 * 
	 * @param roleId
	 *            用户类型ID
	 * @return
	 */
	public List<ModuleJurisdictionDetailVo> selectModuleDetailById(@Param("roleId") int roleId);

	/**
	 * 查询所有模块权限明细的模块
	 * 
	 * @return
	 */
	public List<ModuleJurisdictionDetail> selectAllModule();

	/**
	 * 批量新增模块权限明细
	 * 
	 * @param roleId
	 *            角色ID
	 * @param detailList
	 *            明细集合
	 * @return
	 */
	public int insertModuleDetailByBatch(@Param("roleId") int roleId,
			@Param("detailList") List<ModuleJurisdictionDetail> detailList);

	/**
	 * 批量修改模块权限明细，角色新增时新增
	 * 
	 * @param detailList
	 *            模块权限明细集合
	 * @return
	 */
	public int updateModuleDetailByBatch(@Param("detailList") List<ModuleJurisdictionDetail> detailList);

	/**
	 * 查询所有模块权限明细的角色
	 * 
	 * @return
	 */
	public List<ModuleJurisdictionDetail> selectAllRole();

	/**
	 * 批量新增模块权限明细，模块新增时新增
	 * 
	 * @param moduleId
	 *            模块ID
	 * @param detailList
	 *            明细集合
	 * @return
	 */
	public int addModuleDetailByBatch(@Param("moduleId") int moduleId,
			@Param("detailList") List<ModuleJurisdictionDetail> detailList);

	/**
	 * 通过模块id删除模块权限明细信息，删除模块id时删除
	 * 
	 * @param moduleId
	 *            模块id
	 * @return
	 */
	public int deleteModuleDetailByModuleId(@Param("moduleId") int moduleId);

	/**
	 * 通过角色id删除模块权限明细信息，删除角色id时删除
	 * 
	 * @param roleId
	 *            角色id
	 * @return
	 */
	public int deleteModuleDetailByRoleId(@Param("roleId") int roleId);

}