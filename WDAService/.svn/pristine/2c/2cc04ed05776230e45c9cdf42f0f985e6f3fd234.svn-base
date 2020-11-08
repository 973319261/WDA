package com.gx.mapper;

import com.gx.po.User;
import com.gx.vo.UserVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * UserDAO继承基类
 */
@Repository
public interface UserDAO extends MyBatisBaseDao<User, Integer> {
	/**
	 * 通过账号查询用户信息
	 * 
	 * @param loginName
	 *            用户名
	 * @return
	 */
	public UserVo selectUserInfoByAccount(@Param("userAccount") String userAccount);
	/**
	 * 通过设备ID查询用户信息
	 * @param imei
	 * @return
	 */
	public UserVo selectUserInfoByImei(@Param("imei") String imei);

	/**
	 * 查询用户详情信息
	 * 
	 * @param name
	 *            用户名/区域
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            排序类型
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<UserVo> selectUserDetail(@Param("name") String name, @Param("strName") String strName,
			@Param("sortType") String sortType, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 查询用户详情信息(数据条数)
	 * 
	 * @param name
	 *            用户名/区域
	 * @return
	 */
	public int selectUserDetailRows(@Param("name") String name);

	/**
	 * 查询软件使用已到期的用户信息
	 * 
	 * @return
	 */
	public List<User> selectAllUserInfo();

	/**
	 * 查询员工编号是否重复/新增
	 * 
	 * @param userNum
	 * @return
	 */
	public int selectUserNumberReuse(@Param("userNumber") String userNumber, @Param("userId") int userId);

	/**
	 * 查询员工编号是否重复/修改
	 * 
	 * @param userNum
	 * @param userID
	 * @return
	 */
	public int selectUserNumberReuseTwo(@Param("userNumber") String userNumber, @Param("userId") String userId);

	/**
	 * 查询用户账号是否重复/修改
	 * 
	 * @param loginName
	 * @param userID
	 * @return
	 */
	public int selectAccountResuse(@Param("userAccount") String loginName, @Param("userId") int userId);

	/**
	 * 通过账号查询管理员信息
	 * 
	 * @param loginName
	 * @return
	 */
	public User selectAdminByAccount(@Param("userAccount") String userAccount);

	/**
	 * 查询权限数量
	 * 
	 * @param roleId
	 * @return
	 */
	/**
	 * 通过roleId查询用户是否使用该权限--用于启用、作废权限
	 * 
	 * @param roleId
	 * @return
	 */
	public int selectUserRowByRoleId(@Param("roleId") int roleId);

	/**
	 * 修改查询故障码的访问量
	 * 
	 * @param userId
	 * @return
	 */
	public boolean updateFaultCodeVisit(Integer userId);

	/**
	 * 修改查询分享的访问量
	 * 
	 * @param userId
	 * @return
	 */
	public boolean updateShareVisit(Integer userId);

	/**
	 * 修改查询安全算法的访问量
	 * 
	 * @param userId
	 * @return
	 */
	public boolean updateArithmeticVisit(Integer userId);

	/**
	 * 查询普通用户信息
	 * 
	 * @return
	 */
	public List<User> selectCommonUserInfo();
	/**
	 * 查询所有用户信息
	 * @return
	 */
	public List<UserVo> selectUserInfoAll();
}