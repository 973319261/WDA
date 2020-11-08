package com.gx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gx.po.Admin;

/**
 * AdminDAO继承基类
 */
@Repository
public interface AdminDAO extends MyBatisBaseDao<Admin, Integer> {
	/**
	 * 通过账号查询管理员信息
	 * 
	 * @param adminAccount
	 *            账号
	 * @return
	 */
	public Admin selectAdminByAccount(@Param("adminAccount") String adminAccount);

	/**
	 * 查询管理员信息
	 * 
	 * @param adminAccount
	 *            账号
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Admin> selectAccountInfo(@Param("adminAccount") String adminAccount,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 查询管理员信息(数据总条数)
	 * 
	 * @param adminAccount
	 *            账号
	 * @return
	 */
	public int selectAccountInfoRows(@Param("adminAccount") String adminAccount);

	/**
	 * 查询管理员账号是否存在
	 * 
	 * @param adminAccount
	 *            账号
	 * @param adminId
	 *            管理员ID
	 * @return
	 */
	public int selectAccountWhetherExist(@Param("adminAccount") String adminAccount, @Param("adminId") Integer adminId);

	/**
	 * 查询管理员用户名是否存在
	 * 
	 * @param adminName
	 *            用户名
	 * @param adminId
	 *            管理员ID
	 * @return
	 */
	public int selectNameWhetherExist(@Param("adminName") String adminName, @Param("adminId") Integer adminId);
	
}