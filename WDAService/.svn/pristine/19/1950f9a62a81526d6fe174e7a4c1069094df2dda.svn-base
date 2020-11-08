package com.gx.mapper;

import com.gx.po.Role;
import com.gx.vo.AppendOptionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * RoleDAO继承基类
 */
@Repository
public interface RoleDAO extends MyBatisBaseDao<Role, Integer> {
	/**
	 * 查询权限信息(绑定下拉框)
	 * @return
	 */
	public List<AppendOptionVo> selectRoleInfo();
	
	/**
	 * 查询权限信息
	 * @param roleName 权限类型
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Role> findRoleInfo(@Param("roleName")String roleName, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);
	
	/**
	 * 查询权限信息(数据总条数)
	 * @param roleName 权限类型
	 * @return
	 */
	public int findRoleInfoRows(@Param("roleName")String roleName);
	
	/**
	 * 通过roleId删除职务和权限信息
	 * @param roleId
	 * @return
	 */
	public int deleteRoleInfoByRoleId(@Param("roleId") int roleId);
	
	/**
	 * 查询权限类型名称是否存在
	 * @param roleName 权限类型名称
	 * @param roleId 权限类型id
	 * @return
	 */
	public int findRoleNameInfoRows(@Param("roleName")String roleName,@Param("roleId") int roleId);
}